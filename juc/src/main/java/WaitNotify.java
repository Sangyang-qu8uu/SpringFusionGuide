import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author SangY
 * @Date 2024/7/19 12:04
 **/
public class WaitNotify {
    private static final Logger log = LoggerFactory.getLogger(WaitNotify.class);
    static boolean flag = false;
    static final Object lock = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while (!flag){
                    System.out.println(Thread.currentThread().getName()+"...wating...");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        log.error("t1-error:{}", e.getMessage());
                    }
                }
                System.out.println(Thread.currentThread().getName()+"...flag is true");
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock){
                while (!flag){
                    System.out.println(Thread.currentThread().getName()+"...wating...");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        log.error("t2-error:{}", e.getMessage());
                    }
                }
                System.out.println(Thread.currentThread().getName()+"...flag is true");
            }
        });

        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                lock.notifyAll();
                flag = true;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.error("t3-error:{}", e.getMessage());
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();

    }
}
