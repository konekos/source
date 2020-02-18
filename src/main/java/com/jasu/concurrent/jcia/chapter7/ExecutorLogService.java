package com.jasu.concurrent.jcia.chapter7;

import java.io.PrintWriter;
import java.util.concurrent.*;

/*****************************************
 * @author hjs
 * @date 2020-02-18 1:09
 *****************************************/
public class ExecutorLogService {
    private final ExecutorService exec;
    private static final long TIMEOUT = 10;
    private TimeUnit UNIT = TimeUnit.SECONDS;
    private final PrintWriter printWriter;
    private final BlockingQueue<Runnable> queue;
    private final WriterTask writerTask;

    public ExecutorLogService(PrintWriter printWriter, WriterTask writerTask) {
        this.queue = new ArrayBlockingQueue<Runnable>(10);
        this.exec = new ThreadPoolExecutor(1,1,0,TimeUnit.SECONDS, queue);
        this.printWriter = printWriter;
        this.writerTask = writerTask;
    }

    public void start(){}

    public void stop() throws InterruptedException {
        try{
            exec.shutdown();
            exec.awaitTermination(TIMEOUT, UNIT);
        }finally {
            printWriter.close();
        }
    }


    private class WriterTask implements Runnable{
        private final String msg;

        public WriterTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            printWriter.println(msg);
        }
    }
}
