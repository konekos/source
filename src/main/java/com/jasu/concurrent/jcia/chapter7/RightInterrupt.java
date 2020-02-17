package com.jasu.concurrent.jcia.chapter7;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*****************************************
 * @author hjs
 * @date 2020-02-17 22:25
 *****************************************/
public class RightInterrupt {

    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {

            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable e) {
                    this.t = e;
                }
            }

            void rethrow() {
                if (t != null) {
                    throw launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(() -> taskThread.interrupt(), timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
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
