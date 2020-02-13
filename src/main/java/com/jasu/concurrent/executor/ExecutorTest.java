package com.jasu.concurrent.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-17 22:26
 *****************************************/
public class ExecutorTest {

    private final static AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);
        ExecutorService service = new ThreadPoolExecutor(0, 10, 0, TimeUnit.SECONDS, blockingQueue, r -> {
            Thread t = new Thread(r);
            t.setName("Thread-" + COUNTER.incrementAndGet());
            t.start();
            return t;
        }, ((r, executor) -> {
            //do nothing
        }));

        service.execute(()-> System.out.println(Thread.currentThread().getName()));
    }
}
