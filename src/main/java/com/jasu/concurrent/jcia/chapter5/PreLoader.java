package com.jasu.concurrent.jcia.chapter5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 2:09
 *****************************************/
public class PreLoader {
    private final FutureTask<ProductInfo> future =
            new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
                @Override
                public ProductInfo call() throws Exception {
                    return loadProductInfo();
                }
            });

    private final Thread thread = new Thread(future);

    public void start(){
        thread.start();
    }

    public ProductInfo get() throws InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }
    // 如果 Throwable 是 Error，那么抛出；如果是 RunTime 返回它；否则抛出 IllegalStateException
    private RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not checked", t);
        }
    }


    private ProductInfo loadProductInfo() {
        if (1 == 1) {
            throw new DataLoadException();
        }
        return null;
    }

    public static class ProductInfo{

    }

    public static class DataLoadException extends RuntimeException{

    }
}
