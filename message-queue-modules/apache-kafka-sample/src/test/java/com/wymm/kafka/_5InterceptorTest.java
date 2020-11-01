package com.wymm.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 拦截器的基本思想就是允许应用程序在不修改逻辑的情况下，动态的实现一组可插拔的事件处理逻辑链。它能够在业务操作的前后多个时间点上插入对应的“拦截”逻辑
 */
public class _5InterceptorTest {
    
    /**
     * 生产者使用拦截器
     */
    @Test
    void whenProducerSend_thenUsingInterceptor() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        
        List<String> interceptors = new ArrayList<>();
        interceptors.add(AvgLatencyProducerInterceptor.class.getName());
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++) {
            producer.send(new ProducerRecord<>(KafkaConfig.TEST_TOPIC, Integer.toString(i), Integer.toString(i)));
        }
        
        producer.close();
    }
    
    /**
     * 消费者使用拦截器
     */
    @Test
    void whenEnableAutoCommitIsTrue() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        
        List<String> interceptors = new ArrayList<>();
        interceptors.add(AvgLatencyConsumerInterceptor.class.getName());
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }
        }
    }
}
