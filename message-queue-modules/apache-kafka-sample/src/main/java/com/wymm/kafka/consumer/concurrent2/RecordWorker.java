package com.wymm.kafka.consumer.concurrent2;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class RecordWorker<T, R> implements Runnable {
    
    private final ConsumerRecord<T, R> record;
    
    public RecordWorker(ConsumerRecord<T, R> record) {
        this.record = record;
    }
    
    @Override
    public void run() {
        log.info("partition = {}, offset = {}, key = {}, value = {}", record.partition(), record.offset(), record.key(), record.value());
    }
}
