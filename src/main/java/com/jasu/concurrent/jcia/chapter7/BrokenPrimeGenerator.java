package com.jasu.concurrent.jcia.chapter7;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*****************************************
 * @author hjs
 * @date 2020-02-17 16:28
 *****************************************/
public class BrokenPrimeGenerator extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled;

    public BrokenPrimeGenerator(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            // consumed
        }
    }

    public void cancel(){
        this.cancelled = true;
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(10);
        BrokenPrimeGenerator generator = new BrokenPrimeGenerator(queue);
        generator.start();
        boolean needMorePrimes = true;

        try {
            while (needMorePrimes) {
                consume(queue.take());
            }
        } finally {
            generator.cancel();
        }
    }

    private static void consume(BigInteger take) {
        // consumer
    }
}
