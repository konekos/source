package com.jasu.concurrent.completablefuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-16 21:17
 *****************************************/
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            int i = get();
            completableFuture.complete(i);
        }).start();

        System.out.println("non block");

        Optional.ofNullable(completableFuture.get()).ifPresent(System.out::println);

        completableFuture.whenComplete((i, t) -> {
            Optional.ofNullable(i).ifPresent(System.out::println);
            Optional.ofNullable(t).ifPresent(Throwable::printStackTrace);
        });
        System.out.println("non block");
    }

    private static int get() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
