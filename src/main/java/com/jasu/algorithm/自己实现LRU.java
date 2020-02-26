package com.jasu.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/*****************************************
 * @author hjs
 * @date 2020-02-25 21:12
 *****************************************/
public class 自己实现LRU extends LinkedHashMap {

    private static final int SIZE_LIMIT = 5;

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > SIZE_LIMIT;
    }
}
