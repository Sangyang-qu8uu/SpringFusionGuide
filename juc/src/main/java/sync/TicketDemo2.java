package sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 多线程模拟卖票
 * @Author SangY
 * @Date 2024/7/21 13:46
 **/
public class TicketDemo2 {

    public static int sum = 100;

    private static final Lock lock = new ReentrantLock();

    public static void sellTickets() {

        Runnable runnable = () -> {
            while (true) {
                lock.lock(); // 获取锁

                try {
                    if (sum > 0) {
                        sum--;
                        System.out.println(Thread.currentThread().getName() + "还有多少张票:" + sum);
                    } else {
                        System.out.println(Thread.currentThread().getName() + " 票已售罄");
                        break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();// 释放锁
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
    }

    public static void main(String[] args) {
        sellTickets();
    }
}
