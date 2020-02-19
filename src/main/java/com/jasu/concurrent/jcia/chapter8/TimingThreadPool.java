package com.jasu.concurrent.jcia.chapter8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/*****************************************
 * @author hjs
 * @date 2020-02-19 23:18
 *****************************************/
public class TimingThreadPool extends ThreadPoolExecutor {
    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info("Thread {}: start {}",t,r);
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try{
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.info("Thread: {} end: {} time: {}", t, endTime, taskTime);
        }finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            log.info("Terminated: avg time={}", totalTime.get()/numTasks.get());
        }finally {
            super.terminated();
        }
    }
}
