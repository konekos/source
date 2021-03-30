package com.jasu.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ErrorHandleDemo {
    static Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

    public static void main(String[] args) throws InterruptedException {
//        Flux<String> flux = Flux.just(1, 2, 0)
//                .map(i -> "100 / " + i + " = " + (100 / i)) //this triggers an error with 0
//                .onErrorReturn("Divided by zero :(");// error handling example
//
//        flux.subscribe(System.out::println);


//        Mono<String> m1 = Mono.fromFuture(CompletableFuture.supplyAsync(() -> future(3))).map(s -> s + "   ").publishOn(s);
//        Mono<String> m2 = Mono.fromFuture(CompletableFuture.supplyAsync(() -> future(5))).map(s -> s + "   ").publishOn(s);
//        Mono<String> m3 = Mono.fromFuture(CompletableFuture.supplyAsync(() -> future(10))).map(s -> s + "   ").publishOn(s);
//
//        Flux<String> merge = Flux.merge(m1, m2, m3);
//        merge.subscribe(System.out::println);



        CountDownLatch cdl = new CountDownLatch(3);

        new Thread(()->future(3, cdl),"t1").start();
        new Thread(()->future(5, cdl),"t2").start();
        new Thread(()->future(10, cdl),"t3").start();

        cdl.await();
        Thread.currentThread().join();
    }


    public static String future(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static String future(int seconds, CountDownLatch countDownLatch) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName() + " quit");
        return "success";
    }





}
