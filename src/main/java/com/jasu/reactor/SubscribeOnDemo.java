package com.jasu.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class SubscribeOnDemo {
    public static void main(String[] args) {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4); //1

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    int val = 10 + i;
                    System.out.println(Thread.currentThread().getName() + " val=" + i);
                    return val;
                })  //2
                .subscribeOn(s)  //3
                .map(i -> {
                    String s1 = "value " + i;
                    System.out.println(Thread.currentThread().getName() + "str=" + s1);
                    return s1;
                });  //4

        new Thread(() -> flux.subscribe(s2 -> {
            System.out.println(Thread.currentThread().getName() + " s2=" + s2 );
        })).start(); //5




    }
}
