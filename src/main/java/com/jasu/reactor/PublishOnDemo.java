package com.jasu.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class PublishOnDemo {
    public static void main(String[] args) {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        final Flux<String> flux = Flux
                .fromIterable(list)
                .map(i1 -> {
                    int val = 10 + i1;
                    System.out.println(Thread.currentThread().getName() + " i= " + val);
                    return val;
                })
                .publishOn(s, Integer.MAX_VALUE)
                .map(i2 -> {
                    System.out.println(Thread.currentThread().getName() +" s= " +i2);
                    return "value " + i2;
                });

        new Thread(() -> flux.subscribe(s1 -> {
            System.out.println(Thread.currentThread().getName() + "subcribe ");
        })).start();



    }
}
