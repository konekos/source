package com.jasu.concurrent.jcia.chapter4;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-14 18:56
 *****************************************/
@ThreadSafe
public class SafePoint {
    @GuardedBy("this")private int x, y;

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    public SafePoint(SafePoint p) {
        this(p.get());
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
