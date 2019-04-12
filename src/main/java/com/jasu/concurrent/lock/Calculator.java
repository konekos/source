package com.jasu.concurrent.lock;

/**
 * @author @Jasu
 * @date 2019-01-14 18:02
 */
public class Calculator extends Thread {
    int total;
    boolean isSignalled = false;

    @Override
    public void run() {
        synchronized (this) {
            isSignalled = true;
            for (int i = 0; i < 101; i++) {
                total += i;
            }
            this.notify();
        }

    }
}
        