package com.jasu.kafka.chapter4;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2019-07-10 23:15
 *****************************************/
public class CustomConsumerInterceptor implements ConsumerInterceptor<String, String> {




    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {

        Map<TopicPartition, List<ConsumerRecord<String, String>>> results = new HashMap<>();

        Set<TopicPartition> partitions = records.partitions();
        partitions.forEach(p->{
            List<ConsumerRecord<String, String>> recordInP = records.records(p);
            List<ConsumerRecord<String, String>> recordList = recordInP.stream().filter(r -> "10".equals(r.value())).collect(Collectors.toList());
            results.put(p, recordList);
        });
        return new ConsumerRecords<>(results);
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        System.out.println("=====================begin");
        System.out.println(offsets);
        System.out.println("=====================end");
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
