package com.jasu.concurrent.jcia.chapter7;

import java.util.concurrent.TimeUnit;

/*****************************************
 * @author hjs
 * @date 2020-02-17 22:51
 *****************************************/
public class WaitInterrupt {
    public static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("done");
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("done");
            }
        });

        t1.start();
        t2.start();
        t2.interrupt();
    }


}
