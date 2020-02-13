package com.jasu.concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-21 21:46
 *****************************************/
public class MyLock extends AbstractQueuedSynchronizer {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 get lock");
                try {
                    Thread.sleep(11111111);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally {
                System.out.println("t1 release");
                lock.unlock();
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t2 get lock");
                try {
                    TimeUnit.SECONDS.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
            }
        });
        t2.start();
        t1.interrupt();



    }

    @Override
    protected boolean isHeldExclusively() {
        return super.isHeldExclusively();
    }
    @Override
    protected boolean tryAcquire(int arg) {
        return super.tryAcquire(arg);
    }
    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }
    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }
    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }
}
