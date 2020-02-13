package com.jasu.concurrent.completablefuture;

import io.netty.util.concurrent.CompleteFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-16 21:17
 *****************************************/
public class CompletableFutureTest4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2), Integer::sum)
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2), (r1, r2) -> {
                    System.out.println(r1+r2);
                });


        CompletableFuture.runAsync(() -> System.out.println("A")).thenRun(() -> System.out.println("B")).thenRun(() -> System.out.println("C")).join();
    }
}
