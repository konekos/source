package com.jasu.concurrent.jcia.chapter7;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/*****************************************
 * @author hjs
 * @date 2020-02-18 1:34
 *****************************************/
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShutDown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public List<Runnable> getTasksCancelledAtShutDown() {
        if (!exec.isTerminated()) {
            throw new IllegalStateException("...");
        }
        return new ArrayList<Runnable>(tasksCancelledAtShutDown);
    }

    @Override
    public void execute( Runnable runnable) {
        exec.execute(()->{
            try {
                runnable.run();
            } finally {
                if (isShutdown() && Thread.currentThread().isInterrupted()) {
                    tasksCancelledAtShutDown.add(runnable);
                }
            }
        });
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout,unit);
    }
}
