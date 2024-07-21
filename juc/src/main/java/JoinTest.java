import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 线程执行按照先后顺序
 * @Author SangY
 * @Date 2024/7/19 11:59
 **/
public class JoinTest {
    private static final Logger log = LoggerFactory.getLogger(JoinTest.class);

    public static void main(String[] args) {
        // 创建线程对象
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
        });
        Thread t2 = new Thread(() -> {
            try {
                t1.join();                          // 加入线程t1,只有t1线程执行完毕以后，再次执行该线程
            } catch (InterruptedException e) {
                log.error("t1-error:{}", e.getMessage());
            }
            System.out.println("t2");
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();                              // 加入线程t2,只有t2线程执行完毕以后，再次执行该线程
            } catch (InterruptedException e) {
                log.error("t2-error:{}", e.getMessage());
            }
            System.out.println("t3");
        });
        // 启动线程
        t1.start();
        t2.start();
        t3.start();
    }
}
