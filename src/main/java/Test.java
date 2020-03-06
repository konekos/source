import org.apache.dubbo.remoting.exchange.support.DefaultFuture;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author @Jasu
 * @date 2018-07-11 15:45
 */
public class Test {
    static ExecutorService service = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        while (true) {
            TimeUnit.SECONDS.sleep(10);
        }
    }

    static class A extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A " + Thread.currentThread().getName());
        }
    }

    static class B extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B " + Thread.currentThread().getName());
        }
    }
    static class C extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C " + Thread.currentThread().getName());
        }
    }


}
