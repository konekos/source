package com.jasu.ratelimiter;

/**
 * @author @Jasu
 * @date 2019-02-25 17:00
 */
public class LazyLoader<T> {

    protected volatile T cached;

    public interface Func<T> {
        T apply();
    }
}
