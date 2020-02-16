package com.jasu.concurrent.jcia.chapter5;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 3:06
 *****************************************/
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
