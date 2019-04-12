package com.jasu.ratelimiter;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author @Jasu
 * @date 2018-09-07 11:40
 */
public class Ses {
    private final static PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>(600, Comparator.comparing(integer -> integer==1));

    public static Integer poll() {
        return priorityBlockingQueue.poll();
    }


    public static void addMail(Integer integer) {
        priorityBlockingQueue.add(integer);
    }

    public static void sendMail(Integer integer) {
        System.out.println(integer + " sent");
    }


}
