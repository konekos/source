package com.jasu.kafka.chapter4;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-10 21:49
 *****************************************/
public class ConsumerABothSyncAndSyncCommit {
    private final static Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    public static void main(String[] args) {

        final AtomicInteger counter = new AtomicInteger();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(loadProps());
        consumer.subscribe(Collections.singletonList("test_c"));

        try {
            for (; ; ) {
                //这里只是接收，不要阻塞住。提交异步处理。
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
                //timestamp record发送的时间
                consumerRecords.forEach(r -> {
                    counter.getAndIncrement();
                    System.out.println(r.timestamp());
                    System.out.println(r.key());
                    System.out.println(r.offset());
                    if (counter.get() > 3) {
                        Runtime.getRuntime().halt(-1);
                    }
                });
                //
                consumer.commitAsync();
            }
        }finally {
            //会重试
            consumer.commitSync();
        }



    }

    private static Properties loadProps() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.119.129:9092,192.168.119.130:9092,192.168.119.131:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("group.id", "test_group");
        //自动提交时间10s,测试自动commit
        properties.put("auto.commit.interval.ms", "10000");
        properties.put("client.id", "demo-consumer-client");
        properties.put("enable.auto.commit", "false");
        return properties;
    }
}
