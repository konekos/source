package com.jasu.concurrent.jcia.chapter8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*****************************************
 * @author hjs
 * @date 2020-02-19 2:45
 *****************************************/
public class ThreadDeadLock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(ThreadDeadLock.this::headerTask);
            footer = exec.submit(ThreadDeadLock.this::footerTask);
            String page = readBody();
            // 将发生死锁 —— 由于任务在等待子任务的结果
            return header + page + footer;
        }
    }

    private String readBody() {
        return null;
    }

    private String headerTask() {
        return null;
    }
    private String footerTask() {
        return null;
    }
}
