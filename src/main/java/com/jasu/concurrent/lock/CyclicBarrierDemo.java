package com.jasu.concurrent.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author @Jasu
 * @date 2019-03-29 18:13
 */
public class CyclicBarrierDemo {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
    }
}
