package com.jasu.concurrent.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-16 21:17
 *****************************************/
public class CompletableFutureTest5 {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> 1)
                .runAfterBoth(CompletableFuture.supplyAsync(() -> 1), () -> System.out.println("done")).join();


        //两个有一个结束即可
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        })
                .applyToEither(CompletableFuture.supplyAsync(() -> 2), i -> i * 10)
                .thenAccept(System.out::println);

        //不需要返回值
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).acceptEither(CompletableFuture.supplyAsync(() -> 2), System.out::println);

        //不消费either的结果
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).runAfterEither(CompletableFuture.supplyAsync(() -> 2), ()-> System.out.println("done")).join();


        //所有的执行完
        List<CompletableFuture<Integer>> collect = Arrays.asList(1, 2, 3, 4, 5).stream()
                .map(i -> CompletableFuture.supplyAsync(() -> get(i))).collect(Collectors.toList());
        CompletableFuture[] futures = new CompletableFuture[collect.size()];
        CompletableFuture.allOf(collect.toArray(futures))
                .thenRun(()-> System.out.println("done")).join();

        //任意一个执行完
        List<CompletableFuture<Integer>> collect1 = Arrays.asList(1, 2, 3, 4, 5).stream()
                .map(i -> CompletableFuture.supplyAsync(() -> get(i))).collect(Collectors.toList());
        CompletableFuture[] futures1 = new CompletableFuture[collect1.size()];
        CompletableFuture.anyOf(collect1.toArray(futures1))
                .thenRun(() -> System.out.println("done")).join();
    }



    private static int get(int i) {
        try {
            Thread.sleep(100*i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i * 10;
    }
}
