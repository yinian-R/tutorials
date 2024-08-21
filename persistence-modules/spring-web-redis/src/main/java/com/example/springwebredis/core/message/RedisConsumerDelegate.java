package com.example.springwebredis.core.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Redis 消费委托类
 *
 * @author hrj
 * @since 2023-07-04
 */
@Slf4j
public class RedisConsumerDelegate {
    
    private final List<RedisConsumer> redisConsumerList;
    
    public RedisConsumerDelegate(List<RedisConsumer> redisConsumerList) {
        this.redisConsumerList = redisConsumerList;
    }
    
    public List<PatternTopic> getPatternTopics() {
        return redisConsumerList.stream()
                .map(consumer -> new PatternTopic(consumer.getPatternTopic()))
                .collect(Collectors.toList());
    }
    
    public void handleMessage(String message, String pattern) {
        redisConsumerList.parallelStream()
                .forEach(redisConsumer -> {
                    try {
                        if (redisConsumer.support(message, pattern)) {
                            redisConsumer.handleMessage(message, pattern);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });
    }
}
