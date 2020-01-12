package com.jasu.kafka.chapter3;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-04 21:13
 *****************************************/
public class FireAndForgetSender {

    private final static Logger logger = LoggerFactory.getLogger(FireAndForgetSender.class);

    public static void main(String[] args) {
        Properties props = initProps();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        IntStream.range(0,10).forEach(i->{
            ProducerRecord<String, String> record = new ProducerRecord<>("fsender", String.valueOf(i), i + "ss");
            Future<RecordMetadata> send = producer.send(record);
            while (!send.isDone()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("success");
        });
        producer.flush();
        producer.close();

    }

    private static Properties initProps() {
        final Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.119.129:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
