package com.wymm.kafka.consumer.concurrent1;

import com.wymm.kafka.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 多线程消费者
 */
@Slf4j
public class KafkaConsumerRunner<T, R> implements Runnable {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer<T, R> consumer;
    
    public KafkaConsumerRunner(KafkaConsumer<T, R> consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public void run() {
        try {
            consumer.subscribe(Arrays.asList(KafkaConfig.TEST_TOPIC));
            while (!closed.get()) {
                ConsumerRecords<T, R> records = consumer.poll(Duration.ofMillis(1000));
                // 执行消息处理逻辑
                for (ConsumerRecord<T, R> record : records) {
                    log.info("partition = {}, offset = {}, key = {}, value = {}", record.partition(), record.offset(), record.key(), record.value());
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) {
                throw e;
            }
        } finally {
            consumer.close();
        }
    }
    
    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}
