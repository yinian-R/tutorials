package com.wymm.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.DescribeLogDirsResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class _4AdminTest {
    
    /**
     * 创建 Topic
     */
    @Test
    void createTopic() throws InterruptedException, ExecutionException, TimeoutException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        try (AdminClient adminClient = KafkaAdminClient.create(props)) {
            NewTopic newTopic = new NewTopic("code_topic", 3, (short) 1);
            CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));
            result.all().get(5, TimeUnit.SECONDS);
        }
    }
    
    /**
     * 查询消费组位移
     */
    @Test
    void partitionsToOffsetAndMetadata() throws InterruptedException, ExecutionException, TimeoutException {
        String groupID = "test";
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        try (AdminClient client = AdminClient.create(props)) {
            ListConsumerGroupOffsetsResult result = client.listConsumerGroupOffsets(groupID);
            Map<TopicPartition, OffsetAndMetadata> offsets = result.partitionsToOffsetAndMetadata().get(10, TimeUnit.SECONDS);
            System.out.println(offsets);
        }
    }
    
    /**
     * 获取 Broker 磁盘占用
     */
    @Test
    void logSize() throws ExecutionException, InterruptedException {
        
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        
        try (AdminClient client = AdminClient.create(props)) {
            DescribeLogDirsResult ret = client.describeLogDirs(Collections.singletonList(0)); // 指定Broker id
            long size = 0L;
            for (Map<String, DescribeLogDirsResponse.LogDirInfo> logDirInfoMap : ret.all().get().values()) {
                size += logDirInfoMap.values().stream()
                        .map(logDirInfo -> logDirInfo.replicaInfos)
                        .flatMap(topicPartitionReplicaInfoMap -> topicPartitionReplicaInfoMap.values().stream().map(replicaInfo -> replicaInfo.size))
                        .mapToLong(Long::longValue)
                        .sum();
            }
            System.out.println(size);
        }
    }
    
    
    @Test
    void listTopic() throws InterruptedException, ExecutionException, TimeoutException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVERS);
        try (AdminClient adminClient = KafkaAdminClient.create(props)) {
            ListTopicsResult result = adminClient.listTopics();
            Set<String> topics = result.names().get(5, TimeUnit.SECONDS);
    
            System.out.println(topics);
        }
    }
    
}
