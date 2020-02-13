package com.jasu.concurrent.completablefuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-16 21:17
 *****************************************/
public class CompletableFutureTest2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(CompletableFutureTest2::get)
                .whenComplete((i, t) -> {
                    Optional.ofNullable(i).ifPresent(System.out::println);
                    Optional.ofNullable(t).ifPresent(Throwable::printStackTrace);
                });
        System.out.println("non-block");
        //dont exit
        Thread.currentThread().join();
    }

    private static int get() {
        try {
            System.out.println("Daemon-"+Thread.currentThread().isDaemon()+ " Name-"+ Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
