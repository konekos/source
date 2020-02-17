package com.jasu.concurrent.jcia.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/*****************************************
 * @author hjs
 * @date 2020-02-17 17:10
 *****************************************/
public class PrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            // 允许线程退出
        }
    }

}
