package com.jasu.netty.chapter05;

/**
 * @author @Jasu
 * @date 2018-10-12 14:37
 */
public class Test {
    public static void main(String[] args) {
        double d;
        long startTime = System.currentTimeMillis();

        //do something...
        d = calculate();

        long endTime = System.currentTimeMillis();

        System.out.println("Elapsed Time: " + (endTime - startTime));
    }

    private static double calculate() {
        double d = 0;
        for (int i = 0; i < 1000; i++) {
            d = d + i;
        }
        return d;
    }


}
