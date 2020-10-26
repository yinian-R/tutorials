package com.wymm.kafka;

import com.wymm.kafka.consumer.concurrent1.KafkaConsumerRunner;
import com.wymm.kafka.consumer.concurrent2.KafkaConsumerAndMultiRecordWorkerRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * session.timeout.ms：如果使用者崩溃或在的时间内无法发送心跳
 * max.poll.interval.ms：通过增加预期轮询之间的间隔，您可以给使用者更多的时间来处理从返回的一批记录poll(Duration)。缺点是增加此值可能会延迟组重新平衡，因为使用者只会在轮询调用中加入重新平衡。
 * 您可以使用此设置来限制完成重新平衡的时间，但是如果使用者实际上不能poll经常打电话，则可能会导致进度变慢。
 * max.poll.records：使用此设置可以限制从单个轮询中返回的总记录。这样可以更轻松地预测每个轮询间隔内必须处理的最大值。通过调整此值，您可以减少轮询间隔，这将减少组重新平衡的影响。
 */
@Slf4j
class _2ConsumerTest {
    
    /**
     * 自动提交位移
     * 设置enable.auto.commit意味着位移将自动以配置auto.commit.interval.ms控制的频率提交。
     */
    @Test
    void whenEnableAutoCommitIsTrue() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
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
    
    /**
     * 手动偏移控制
     * 通过这种方式，Kafka提供了通常称为“至少一次”的交付保证，因为每条记录可能会交付一次，但在失败的情况下可以重复
     */
    @Test
    void whenEnableAutoCommitIsFalse() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC));
            final int minBatchSize = 200;
            List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    buffer.add(record);
                }
                if (buffer.size() >= minBatchSize) {
                    // insertIntoDb(buffer);
                    System.out.println("buffer.size:" + buffer.size());
                    consumer.commitSync();
                    buffer.clear();
                }
            }
        }
    }
    
    /**
     * 手动提交位移（推荐）
     * commitSync 和 commitAsync 组合使用才能到达最理想的效果
     */
    @Test
    void whenEnableAutoCommitIsFalse_thenCommitAsync_thenCommitSync() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        try {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                //process(records); // 处理消息
                consumer.commitAsync(); // 使用异步提交规避阻塞
            }
        } catch (Exception e) {
            //handle(e); // 处理异常
        } finally {
            try {
                consumer.commitSync(); // 最后一次提交使用同步阻塞式提交
            } finally {
                consumer.close();
            }
        }
    }
    
    /**
     * 手动偏移控制，按分区来手动提交位移
     * 常用于在中间进行提交位移。比如前面这个 5000 条消息的例子，你可能希望每处理完 100 条消息就提交一次位移，这样能够避免大批量的消息重新消费。
     * PS:提交的位移应始终是应用程序将读取的下一条消息的位移。 因此，在调用时commitSync(offsets)，应在最后处理的消息的位移上加上一个。
     */
    @Test
    void whenEnableAutoCommitIsFalse_thenCommitSyncAndOffsets() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC));
            
            int commitOffsetSize = 100;
            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
            AtomicInteger offsetsCount = new AtomicInteger(0);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : records) {
                    // process(record);
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    
                    offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                    if (offsetsCount.incrementAndGet() == commitOffsetSize) {
                        commitSyncOffsets(consumer, offsets, offsetsCount);
                    }
                }
                if (records.count() == 0 && offsetsCount.get() != 0) {
                    commitSyncOffsets(consumer, offsets, offsetsCount);
                }
            }
        }
    }
    
    public void commitSyncOffsets(KafkaConsumer<String, String> consumer, Map<TopicPartition, OffsetAndMetadata> offsets, AtomicInteger offsetsCount) {
        consumer.commitSync(offsets);
        offsets.clear();
        offsetsCount.set(0);
    }
    
    public void commitAsyncOffsets(KafkaConsumer<String, String> consumer, Map<TopicPartition, OffsetAndMetadata> offsets, AtomicInteger offsetsCount) {
        consumer.commitAsync(offsets, (o, e) -> {
            if (e != null) {
                log.error("Commit failed for " + o, e);
            } else if (log.isDebugEnabled()) {
                log.debug("Commits for " + o + " completed");
            }
        });
        offsets.clear();
        offsetsCount.set(0);
    }
    
    /**
     * 手动提交位移 + 按分区来手动提交位移（推荐）
     * commitSync 和 commitAsync 组合使用才能到达最理想的效果
     */
    @Test
    void whenEnableAutoCommitIsFalse_thenCommitAsyncAndOffsets_thenCommitSync() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        try {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC));
            
            int commitOffsetSize = 100;
            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
            AtomicInteger offsetsCount = new AtomicInteger(0);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : records) {
                    // process record
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    
                    offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                    if (offsetsCount.incrementAndGet() == commitOffsetSize) {
                        commitAsyncOffsets(consumer, offsets, offsetsCount); // 使用异步提交规避阻塞
                    }
                }
                if (records.count() == 0 && offsetsCount.get() != 0) {
                    commitAsyncOffsets(consumer, offsets, offsetsCount); // 使用异步提交规避阻塞
                }
            }
        } catch (Exception e) {
            //handle(e); // 处理异常
        } finally {
            try {
                consumer.commitSync(); // 最后一次提交使用同步阻塞式提交
            } finally {
                consumer.close();
            }
        }
    }
    
    
    /**
     * 手动分配分区消费
     * assign 手动分配分区不能和 subscribe 动态分配混合一起使用
     */
    @Test
    void manualPartitionAssignment() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            
            String topic = KafkaConfig.TEST_TOPIC;
            TopicPartition partition0 = new TopicPartition(topic, 0);
            TopicPartition partition1 = new TopicPartition(topic, 3);
            consumer.assign(Arrays.asList(partition0, partition1));
            
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }
        }
    }
    
    /**
     * 重设消费者位移
     * 推荐使用命令重设消费者位移
     */
    @Test
    void controllingTheConsumerPosition() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    for (TopicPartition partition : partitions) {
                        long position = consumer.position(partition);
                        System.out.println(position);
                    }
                }
                
                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    //for (TopicPartition partition : partitions) {
                    //    if (partition.equals(new TopicPartition(KafkaConfig.TEST_TOPIC, 0))) {
                    //        // 指定分区位移
                    //        consumer.seek(partition, 1000);
                    //    }
                    //}
                    // 指定分区从最早位移
                    consumer.seekToBeginning(partitions);
                    // 指定分区从最新位移
                    //consumer.seekToEnd(partitions);
                }
            });
            
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n", record.partition(), record.offset(), record.key(), record.value());
                }
            }
        }
    }
    
    /**
     * 事务消费者
     */
    @Test
    void transactionConsumer() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
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
    
    /**
     * 多线程消费者
     * 消费者启用多个线程，每个线程维护专属的 KafkaConsumer 实例，负责完整的消费消息获取、消息处理流程。
     */
    @Test
    void multiThread() throws InterruptedException {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        
        int nThreads = 3;
        
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        
        List<KafkaConsumerRunner<String, String>> runners = Stream
                .generate((() -> {
                    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                    return new KafkaConsumerRunner<>(consumer);
                }))
                .limit(nThreads)
                .peek(executorService::execute)
                .collect(Collectors.toList());
        
        
        Runtime.getRuntime().addShutdownHook(new Thread("kafka-consumer-runner-shutdown-hoot") {
            @Override
            public void run() {
                runners.forEach(KafkaConsumerRunner::shutdown);
            }
        });
        
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
    
    /**
     * 多线程消费者
     * 消费者使用单或者多线程获取消息，同时创建多个消费者线程执行消息处理逻辑。
     */
    @Test
    void multiThreadAndMultiRecordThread() throws InterruptedException {
        log.info("go");
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        
        int nThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        ExecutorService recordExecutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        List<KafkaConsumerAndMultiRecordWorkerRunner<String, String>> runners = Stream
                .generate((() -> {
                    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                    return new KafkaConsumerAndMultiRecordWorkerRunner<>(consumer, recordExecutors);
                }))
                .limit(nThreads)
                .peek(executorService::execute)
                .collect(Collectors.toList());
        
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("kafka-consumer-runner-shutdown-hoot") {
            @Override
            public void run() {
                runners.forEach(KafkaConsumerAndMultiRecordWorkerRunner::shutdown);
                countDownLatch.countDown();
            }
        });
        
        countDownLatch.await();
    }
    
    
    /**
     * 使用认证机制 SCRAM-SHA-256 消费消息
     */
    @Test
    void usingSaslScram_thenConsumer() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"reader\" password=\"reader\";");
        
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(KafkaConfig.TEST_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }
        }
    }
}
