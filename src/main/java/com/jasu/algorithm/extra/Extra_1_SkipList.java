package com.jasu.algorithm.extra;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 *  * Head nodes          Index nodes
 *      * +-+    right        +-+                      +-+
 *      * |2|---------------->| |--------------------->| |->null
 *      * +-+                 +-+                      +-+
 *      *  | down              |                        |
 *      *  v                   v                        v
 *      * +-+            +-+  +-+       +-+            +-+       +-+
 *      * |1|----------->| |->| |------>| |----------->| |------>| |->null
 *      * +-+            +-+  +-+       +-+            +-+       +-+
 *      *  v              |    |         |              |         |
 *      * Nodes  next     v    v         v              v         v
 *      * +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+
 *      * | |->|A|->|B|->|C|->|D|->|E|->|F|->|G|->|H|->|I|->|J|->|K|->null
 *      * +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+  +-+
 *
 *      https://www.cnblogs.com/yangming1996/p/8084819.html
 * @author huangjiashu
 * @date 2019/12/10
 **/
public class Extra_1_SkipList {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("dd,d", "1");
        map.remove("dddd");


        outer: for (; ; ) {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
                    break outer;
                }
            }
        }
    }
}
