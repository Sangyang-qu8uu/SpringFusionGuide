import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.sql.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSync {

    private static final int BATCH_SIZE = 10000; // 每批处理的数据量
    private static final int THREAD_COUNT = 12; // 线程数量
    private static final int MAX_CONCURRENT_REQUESTS = 12; // 最大并发请求数量
    private static final Logger logger = Logger.getLogger(DataSync.class.getName());

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        Semaphore semaphore = new Semaphore(MAX_CONCURRENT_REQUESTS);

        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 2300, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder ->
                                httpClientBuilder.setMaxConnTotal(100)
                                        .setMaxConnPerRoute(100)
                                        .setDefaultIOReactorConfig(IOReactorConfig.custom()
                                                .setIoThreadCount(THREAD_COUNT)
                                                .setSoTimeout(60000)
                                                .setConnectTimeout(60000)
                                                .build())
                        )
                        .setRequestConfigCallback(requestConfigBuilder ->
                                requestConfigBuilder.setConnectTimeout(10000)
                                        .setSocketTimeout(60000)
                        )
        );

        try {
            int totalRows = getTotalRows();
            int totalBatches = (int) Math.ceil((double) totalRows / BATCH_SIZE);

            // 创建Elasticsearch索引
            createIndex(esClient, "your_index");

            for (int i = 0; i < totalBatches; i++) {
                int offset = i * BATCH_SIZE;
                semaphore.acquire();
                executorService.submit(new DataSyncTask(esClient, latch, semaphore, offset, BATCH_SIZE));
            }

            latch.await();
            long endTime = System.currentTimeMillis();
            logger.log(Level.INFO, "Data synchronization completed. Total time: {0} ms", (endTime - startTime));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
                esClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int getTotalRows() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
             PreparedStatement countStmt = connection.prepareStatement("SELECT COUNT(*) FROM your_table");
             ResultSet resultSet = countStmt.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    private static void createIndex(RestHighLevelClient esClient, String indexName) {
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            boolean exists = esClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

            if (!exists) {
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);
                logger.log(Level.INFO, "Index {0} created: {1}", new Object[]{indexName, createIndexResponse.isAcknowledged()});
            } else {
                logger.log(Level.INFO, "Index {0} already exists.", indexName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class DataSyncTask implements Runnable {

        private final RestHighLevelClient esClient;
        private final CountDownLatch latch;
        private final Semaphore semaphore;
        private final int offset;
        private final int batchSize;

        public DataSyncTask(RestHighLevelClient esClient, CountDownLatch latch, Semaphore semaphore, int offset, int batchSize) {
            this.esClient = esClient;
            this.latch = latch;
            this.semaphore = semaphore;
            this.offset = offset;
            this.batchSize = batchSize;
        }

        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root")) {
                String query = "SELECT * FROM your_table LIMIT ? OFFSET ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, batchSize);
                    stmt.setInt(2, offset);

                    try (ResultSet rs = stmt.executeQuery()) {
                        BulkRequest bulkRequest = new BulkRequest();

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            int age = rs.getInt("age");
                            String address = rs.getString("address");

                            String json = String.format("{\"name\":\"%s\", \"age\":%d, \"address\":\"%s\"}", name, age, address);
                            bulkRequest.add(new IndexRequest("your_index").id(String.valueOf(id)).source(json, XContentType.JSON));
                        }

                        esClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                            @Override
                            public void onResponse(BulkResponse bulkResponse) {
                                if (bulkResponse.hasFailures()) {
                                    logger.log(Level.SEVERE, "Bulk request has failures: {0}", bulkResponse.buildFailureMessage());
                                } else {
                                    logger.log(Level.INFO, "Successfully inserted batch with offset: {0}, batch size: {1}", new Object[]{offset, batchSize});
                                }
                                semaphore.release();
                                latch.countDown();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                e.printStackTrace();
                                semaphore.release();
                                latch.countDown();
                            }
                        });

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                semaphore.release();
                latch.countDown();
            }
        }
    }
}
