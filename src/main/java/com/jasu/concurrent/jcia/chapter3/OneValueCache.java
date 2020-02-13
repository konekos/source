package com.jasu.concurrent.jcia.chapter3;

import javax.annotation.concurrent.Immutable;
import java.math.BigInteger;
import java.util.Arrays;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-02-12 22:56
 *****************************************/
@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = Arrays.copyOf(lastFactors,lastFactors.length);
    }

    public BigInteger[] getLastFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i)) {
            return null;
        }else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }

    @Override
    public String toString() {
        return "OneValueCache{" +
                "lastNumber=" + lastNumber +
                ", lastFactors=" + Arrays.toString(lastFactors) +
                '}';
    }
}
