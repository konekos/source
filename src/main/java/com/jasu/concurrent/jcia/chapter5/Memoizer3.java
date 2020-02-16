package com.jasu.concurrent.jcia.chapter5;

import java.util.Map;
import java.util.concurrent.*;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 3:08
 *****************************************/
public class Memoizer3<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public Memoizer3(Computable<A,V> c) {
        this.c = c;
    }


    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };

            FutureTask<V> ft = new FutureTask<>(callable);
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }

        try {
            return f.get();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    private RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not checked", t);
        }
    }
}
