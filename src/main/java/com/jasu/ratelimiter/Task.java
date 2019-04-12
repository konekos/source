package com.jasu.ratelimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author @Jasu
 * @date 2018-09-07 11:41
 */
public class Task implements Runnable {
    private final static MinimalisticTokenBucket rateLimiter = new MinimalisticTokenBucket(3, 3, 10*1000);


    public void init() {
        Thread t = new Thread(this);
        t.setName("sendMailTask");
        t.start();
    }



    @Override
    public void run() {
        while (true) {
            sendMailThroughLimiter();
        }
    }

    private void sendMailThroughLimiter() {
        Integer integer = Ses.poll();
        if (integer == null) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        while (!rateLimiter.tryConsume(1)) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Ses.sendMail(integer);
    }
}
