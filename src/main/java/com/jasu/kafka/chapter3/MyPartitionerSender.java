package com.jasu.kafka.chapter3;

import java.util.Properties;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-08 22:02
 *****************************************/
public class MyPartitionerSender {

    private static Properties initProps() {
        final Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.119.129:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "com.jasu.kafka.chapter3.MyPartitioner");
        return props;
    }

}
