package com.wymm.kafka.consumer.concurrent2;

import com.wymm.kafka.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 多线程消费者
 */
@Slf4j
public class KafkaConsumerAndMultiRecordWorkerRunner<T, R> implements Runnable {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer<T, R> consumer;
    private final ExecutorService recordExecutors;
    
    public KafkaConsumerAndMultiRecordWorkerRunner(KafkaConsumer<T, R> consumer, ExecutorService recordExecutors) {
        this.consumer = consumer;
        this.recordExecutors = recordExecutors;
    }
    
    @Override
    public void run() {
        try {
            consumer.subscribe(Arrays.asList(KafkaConfig.TEST_TOPIC));
            while (!closed.get()) {
                ConsumerRecords<T, R> records = consumer.poll(Duration.ofSeconds(1));
                // 执行消息处理逻辑
                for (ConsumerRecord<T, R> record : records) {
                    recordExecutors.submit(new RecordWorker<>(record));
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
