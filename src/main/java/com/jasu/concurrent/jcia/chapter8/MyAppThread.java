package com.jasu.concurrent.jcia.chapter8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/*****************************************
 * @author hjs
 * @date 2020-02-19 22:53
 *****************************************/
public class MyAppThread extends Thread{
    private static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = LoggerFactory.getLogger(MyAppThread.class);

    public MyAppThread(Runnable r, String name) {
        super(r, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler((thread,t)->log.error(thread.getName() + " " + t));
    }

    @Override
    public void run(){
        boolean debug = debugLifecycle;
        if (debug) {
            log.debug("Created " + getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        }finally {
            alive.decrementAndGet();
            if (debug) {
                log.debug("Exiting " + getName());
            }
        }
    }

    public static boolean isDebugLifecycle() {
        return debugLifecycle;
    }

    public static AtomicInteger getCreated() {
        return created;
    }

    public static AtomicInteger getAlive() {
        return alive;
    }

    public static void setDebugLifecycle(boolean debugLifecycle) {
        MyAppThread.debugLifecycle = debugLifecycle;
    }
}
