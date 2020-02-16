package com.jasu.concurrent.jcia.chapter5;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 3:08
 *****************************************/
public class Memoizer2<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public Memoizer2(Computable<A,V> c) {
        this.c = c;
    }


    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
