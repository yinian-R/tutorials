package com.wymm.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class AvgLatencyProducerInterceptor implements ProducerInterceptor<String, String> {
    
    //private Jedis jedis;
    
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        //jedis.incr("totalSentMessage");
        log.info("发送：" + record.value());
        return record;
    }
    
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
    
    }
    
    @Override
    public void close() {
    
    }
    
    @Override
    public void configure(Map<String, ?> configs) {
    
    }
}
