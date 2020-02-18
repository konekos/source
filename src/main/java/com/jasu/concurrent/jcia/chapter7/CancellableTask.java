package com.jasu.concurrent.jcia.chapter7;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/*****************************************
 * @author hjs
 * @date 2020-02-17 23:59
 *****************************************/
public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
