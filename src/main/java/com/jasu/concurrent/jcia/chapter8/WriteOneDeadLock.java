package com.jasu.concurrent.jcia.chapter8;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*****************************************
 * @author hjs
 * @date 2020-02-19 2:53
 *****************************************/
public class WriteOneDeadLock {
    static ExecutorService exec = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        exec.execute(()->{
            try {
                exec.submit(() -> {
                    // do nothing
                }).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
