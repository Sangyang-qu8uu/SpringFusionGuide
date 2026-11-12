import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */
public class MysqlTest {

    @Test
    public void test1() throws Exception {
        // c3p0的连接池数据源
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // 数据库的驱动
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        // 获取连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        // 释放资源
        connection.close();
    }

    @Test
    public void test2() throws Exception {
        // Druid的连接池数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        // 获取连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        // 释放资源
        connection.close();
    }
    @Test
    public void test3() throws Exception {
        // 抽取配置文件Properties
        // 读取文件Properties
        DruidDataSource dataSource = new DruidDataSource();

        // 核心：使用 ResourceBundle 读取 jdbc.properties 文件
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

        // 通过 key 获取 value
        dataSource.setDriverClassName(bundle.getString("jdbc.driver"));
        dataSource.setUrl(bundle.getString("jdbc.url"));
        dataSource.setUsername(bundle.getString("jdbc.username"));
        dataSource.setPassword(bundle.getString("jdbc.password"));

        // 获取连接
        DruidPooledConnection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void testDataSource() throws Exception {
        // 测试从容器当中获取数据源
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");

        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        connection.close(); // 释放资源
    }
}
