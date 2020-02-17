package com.jasu.concurrent.jcia.chapter7;

import com.jasu.ratelimiter.Task;

import java.util.concurrent.BlockingQueue;

/*****************************************
 * @author hjs
 * @date 2020-02-17 18:35
 *****************************************/
public class UncancelableTask {

    private Task getNextTask(BlockingQueue<Task> queue) {

        boolean interrupted = false;

        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
