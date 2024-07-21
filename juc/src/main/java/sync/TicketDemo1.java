package sync;

/**
 * @Description 多线程模拟卖票
 * @Author SangY
 * @Date 2024/7/21 13:46
 **/
public class TicketDemo1 {

    public static int sum = 100;


    public static void sellTickets() {

        Runnable runnable = () -> {
            while (true) {
                synchronized (TicketDemo1.class) {
                    if (sum > 0) {
                        sum--;
                        System.out.println(Thread.currentThread().getName() + "还有多少张票:" + sum);
                    } else {
                        System.out.println(Thread.currentThread().getName() + " 票已售罄");
                        break;
                    }
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
