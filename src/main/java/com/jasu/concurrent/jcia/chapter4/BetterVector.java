package com.jasu.concurrent.jcia.chapter4;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Vector;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-14 22:23
 *****************************************/
@ThreadSafe
public class BetterVector<T> extends Vector<T> {
    public synchronized boolean putIfAbsent(T e) {
        boolean absent = !contains(e);
        if (absent) {
            add(e);
        }
        return absent;
    }
}
