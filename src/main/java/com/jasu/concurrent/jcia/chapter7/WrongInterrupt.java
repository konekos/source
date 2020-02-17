package com.jasu.concurrent.jcia.chapter7;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*****************************************
 * @author hjs
 * @date 2020-02-17 21:18
 *****************************************/
public class WrongInterrupt {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(() -> taskThread.interrupt(), timeout, unit);
        r.run();
    }
}
