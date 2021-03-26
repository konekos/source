package com.jasu.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class PublishOnDemo {
    public static void main(String[] args) {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 1000000)
                .map(i -> 10 + i)
                .publishOn(s)
                .map(i -> {
                    System.out.println(Thread.currentThread().getName() +" " +i);
                    return "value " + i;
                });

        new Thread(() -> flux.subscribe(s1 -> {
        })).start();
    }
}
