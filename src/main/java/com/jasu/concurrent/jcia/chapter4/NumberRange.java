package com.jasu.concurrent.jcia.chapter4;

import java.util.concurrent.atomic.AtomicInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-13 22:20
 *****************************************/

public class NumberRange {

    // 不变性条件：lower <= upper
    private final AtomicInteger lower = new AtomicInteger();
    private final AtomicInteger upper = new AtomicInteger();

    public void setLower(int i) {
        // 注意————不安全的“先检查后执行”
        if (i > upper.get()) {
            throw new IllegalArgumentException("...");
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        // 注意————不安全的先检查后执行
        if (i < lower.get()) {
            throw new IllegalArgumentException("...");
        }
        upper.set(i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }

}
