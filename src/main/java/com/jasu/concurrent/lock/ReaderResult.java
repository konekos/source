package com.jasu.concurrent.lock;

/**
 * @author @Jasu
 * @date 2019-01-14 18:05
 */
public class ReaderResult extends Thread {
    Calculator c;

    public ReaderResult(Calculator c) {
        this.c = c;
    }

    @Override
    public void run() {
        synchronized (c) {
            if (!c.isSignalled) {
                try {
                    System.out.println(Thread.currentThread() + "等待...");
                    c.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "结果:" + c.total);
            }
        }
    }
}
