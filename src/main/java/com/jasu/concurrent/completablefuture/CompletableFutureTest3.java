package com.jasu.concurrent.completablefuture;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-16 21:17
 *****************************************/
public class CompletableFutureTest3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture.supplyAsync(CompletableFutureTest3::get)
//                .thenApply(CompletableFutureTest3::multiply)
//                .whenComplete((i, t) -> {
//                    Optional.ofNullable(i).ifPresent(System.out::println);
//                    Optional.ofNullable(t).ifPresent(Throwable::printStackTrace);
//                }).join();

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sum = list.stream().map(i -> CompletableFuture.supplyAsync(() -> iGet(i)))
                .map(cf -> cf.thenApplyAsync(CompletableFutureTest3::multiply))
                .map(cf -> cf.whenCompleteAsync((i, t) -> {
                    Optional.ofNullable(i).ifPresent(System.out::println);
                    Optional.ofNullable(t).ifPresent(Throwable::printStackTrace);
                }))
                .map(CompletableFuture::join)
                .mapToInt(value -> value).sum();
        System.out.println("sum: " + sum);
    }

    private static int get() {
        try {
            System.out.println("Daemon-" + Thread.currentThread().isDaemon() + " Name-" + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static int iGet(int i) {
        try {
            System.out.println("i=" + i + " Name: " + Thread.currentThread().getName());
            Thread.sleep(i * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static int multiply(int i) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i * 1000;
    }
}
