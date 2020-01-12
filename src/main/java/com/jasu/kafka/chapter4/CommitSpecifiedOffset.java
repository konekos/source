package com.jasu.kafka.chapter4;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-10 22:18
 *****************************************/
public class CommitSpecifiedOffset {
    private final static Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    public static void main(String[] args) {

        final AtomicInteger counter = new AtomicInteger();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(loadProps());
        consumer.subscribe(Collections.singletonList("test_c"));
        //提交指定offset的原因，处理record中间出错时，后面的也不能继续处理，那么指定offset提交，防止丢数据。
        Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
        TopicPartition tp = new TopicPartition("test_c", 1);
        OffsetAndMetadata om = new OffsetAndMetadata(14, "no metadata");
        map.put(tp, om);

        for (; ; ) {
            //这里只是接收，不要阻塞住。提交异步处理。
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
            //timestamp record发送的时间
            consumerRecords.forEach(r -> {
                counter.getAndIncrement();
                System.out.println(r.timestamp());
                System.out.println(r.key());
                System.out.println(r.offset());
            });
            //自己写逻辑控制commit  同步 阻塞住。会重试
            consumer.commitSync(map);
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
