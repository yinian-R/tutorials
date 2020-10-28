package com.wymm.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

@Slf4j
public class AvgLatencyConsumerInterceptor implements ConsumerInterceptor<String, String> {
    
    //private Jedis jedis; //省略Jedis初始化
    
    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        long lantency = 0L;
        for (ConsumerRecord record : records) {
            lantency += (System.currentTimeMillis() - record.timestamp());
        }
        //jedis.incrBy("totalLatency", lantency);
        //long totalLatency = Long.parseLong(jedis.get("totalLatency"));
        //long totalSentMsgs = Long.parseLong(jedis.get("totalSentMessage"));
        //jedis.set("avgLatency", String.valueOf(totalLatency / totalSentMsgs));
        log.info("this ConsumerRecords avgLantency:" + lantency / records.count());
        return records;
    }
    
    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        
    }
    
    @Override
    public void close() {
        
    }
    
    @Override
    public void configure(Map<String, ?> configs) {
        
    }
}
