package com.jasu.kafka.chapter3;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-08 21:55
 *****************************************/
public class MyPartitioner implements Partitioner {

    private final static String[] BIZ_TYPE = new String[]{"LOGIN", "LOGOFF", "ORDER"};
    private final static String LOGIN = "LOGIN";
    private final static String LOGOFF = "LOGOFF";
    private final static String ORDER = "ORDER";


    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        if (keyBytes == null || keyBytes.length == 0) {
            throw new IllegalArgumentException("key is required biz type");
        }
        switch (key.toString().toUpperCase()) {
            case LOGIN:
                return 0;
            case LOGOFF:
                return 1;
            case ORDER:
                return 2;
                default:
                    throw new IllegalArgumentException("key is invalid");
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
