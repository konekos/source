package com.jasu.concurrent.jcia.chapter7;

import javax.annotation.concurrent.GuardedBy;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/*****************************************
 * @author hjs
 * @date 2020-02-18 0:59
 *****************************************/
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    public LogService(BlockingQueue<String> queue, LoggerThread loggerThread, PrintWriter writer) {
        this.queue = queue;
        this.loggerThread = loggerThread;
        this.writer = writer;
    }

    @GuardedBy("this")
    private boolean isShutdown;
    @GuardedBy("this")
    private int reservations;

    public void start() {
        loggerThread.start();
    }

    public void stop(){
        synchronized (this) {
            isShutdown = true;
            loggerThread.interrupt();
        }
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("...");
            }
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        @Override
        public void run() {
            try{
                while (true) {
                    try {
                        synchronized (this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                            String msg = queue.take();
                            synchronized (this) {
                                --reservations;
                            }
                            writer.println(msg);
                        }
                    } catch (InterruptedException e) {
                        // retry
                    }
                }
            }finally {
                writer.close();
            }
        }
    }
}
