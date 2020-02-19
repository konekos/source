package com.jasu.concurrent.jcia.chapter8;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/*****************************************
 * @author hjs
 * @date 2020-02-19 4:00
 *****************************************/
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, Semaphore semaphore) {
        this.exec = exec;
        this.semaphore = semaphore;
    }

    public void submitTask(final Runnable task) throws InterruptedException {
        semaphore.acquire();

        try {
            exec.execute(()->{
                try{
                    task.run();
                }finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
