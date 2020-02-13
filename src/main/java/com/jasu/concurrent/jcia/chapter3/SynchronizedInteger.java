package com.jasu.concurrent.jcia.chapter3;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-12 1:17
 *****************************************/
@ThreadSafe
public class SynchronizedInteger {
    @GuardedBy("this") private  int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}
