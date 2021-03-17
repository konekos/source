package com.jasu.kafka.chapter3;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.stream.IntStream;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-04 22:17
 *****************************************/
public class AsyncSender {
    public static void main(String[] args) {
        Properties props = initProps();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        IntStream.range(0,10).forEach(i->{
            ProducerRecord<String, String> record = new ProducerRecord<>("fsender", String.valueOf(i), i + "ss");
            producer.send(record, (r, e) -> {
                if (e == null) {
                    System.out.println(r.offset());
                }
            });
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
