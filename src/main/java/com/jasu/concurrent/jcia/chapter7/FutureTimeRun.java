package com.jasu.concurrent.jcia.chapter7;

import java.util.concurrent.*;

/*****************************************
 * @author hjs
 * @date 2020-02-17 22:36
 *****************************************/
public class FutureTimeRun {

    private static final ExecutorService taskExec = Executors.newFixedThreadPool(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> future = taskExec.submit(r);

        try {
            future.get(timeout, unit);
        } catch (ExecutionException e) {
            // 任务取消
        } catch (TimeoutException e) {
            throw launderThrowable(e.getCause());
        }finally {
            future.cancel(true);
        }


    }

    private static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not checked", t);
        }
    }
}
