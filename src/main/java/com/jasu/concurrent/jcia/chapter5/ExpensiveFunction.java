package com.jasu.concurrent.jcia.chapter5;

import java.math.BigInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-16 3:11
 *****************************************/
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // 长时间计算
        return new BigInteger(arg);
    }
}
