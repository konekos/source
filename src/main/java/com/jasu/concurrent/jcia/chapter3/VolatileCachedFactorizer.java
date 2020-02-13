package com.jasu.concurrent.jcia.chapter3;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-12 23:01
 *****************************************/
public class VolatileCachedFactorizer {
    private volatile OneValueCache cache = new OneValueCache(null, new BigInteger[0]);

    public void service(long num) {
        BigInteger i = new BigInteger(String.valueOf(num));
        BigInteger[] factors = cache.getLastFactors(i);
        System.out.println(i + "   " + Thread.currentThread().getName() + "   " + factors);
        try {
            TimeUnit.SECONDS.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i + "   " + Thread.currentThread().getName() + "   " + factors);
        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
        }
        System.out.println("done");
        System.out.println(Thread.currentThread().getName() + "   " +cache);
    }

    private BigInteger[] factor(BigInteger i) {
        int value = i.intValue();
        BigInteger[] bigIntegers = new BigInteger[value];
        for (int j = 0; j < value; j++) {
            bigIntegers[j] = new BigInteger(String.valueOf(j));
        }
        return bigIntegers;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileCachedFactorizer factorizer = new VolatileCachedFactorizer();
        new Thread(() -> factorizer.service(1L),"t1").start();
        new Thread(() -> factorizer.service(2L),"t2").start();
        new Thread(() -> factorizer.service(3L),"t3").start();
    }
}
