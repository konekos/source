package com.jasu.concurrent.jcia.chapter7;

import javax.annotation.concurrent.GuardedBy;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*****************************************
 * @author hjs
 * @date 2020-02-18 1:42
 *****************************************/
abstract public class TrackingExecutorService {
    private volatile TrackingExecutor exec;
    private static final long TIMEOUT = 10;
    private TimeUnit UNIT = TimeUnit.SECONDS;

    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<>();

    public synchronized void start() {
        exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCarwlTask(url);
        }
        urlsToCrawl.clear();
    }

    public synchronized void stop() throws InterruptedException {
        try {
            saveUncrawled(exec.shutdownNow());
            if (exec.awaitTermination(TIMEOUT, UNIT)) {
                saveUncrawled(exec.getTasksCancelledAtShutDown());
            }
        } finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUncrawled(List<Runnable> uncrawled) {
        for (Runnable runnable : uncrawled) {
            urlsToCrawl.add((((CrawlTask) runnable)).getUrl());
        }
    }

    private void submitCarwlTask(URL url) {
        exec.execute(new CrawlTask(url));
    }


    private class CrawlTask implements Runnable {

        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCarwlTask(link);
            }
        }

        public URL getUrl() {
            return url;
        }
    }
}
