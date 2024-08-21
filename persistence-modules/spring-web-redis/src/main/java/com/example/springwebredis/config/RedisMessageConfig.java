package com.example.springwebredis.config;

import com.example.springwebredis.core.message.RedisConsumer;
import com.example.springwebredis.core.message.RedisConsumerDelegate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisMessageConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @ConditionalOnBean(RedisConsumer.class)
    @Bean("commonRedisMessageListenerContainer")
    public RedisMessageListenerContainer commonRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                      MessageListenerAdapter commonAnswerMessageListenerAdapter,
                                                                      RedisConsumerDelegate redisConsumerDelegate) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(commonAnswerMessageListenerAdapter, redisConsumerDelegate.getPatternTopics());
        return container;
    }

    @ConditionalOnBean(RedisConsumer.class)
    @Bean("commonAnswerMessageListenerAdapter")
    public MessageListenerAdapter commonAnswerMessageListenerAdapter(RedisConsumerDelegate redisConsumerDelegate) {
        return new MessageListenerAdapter(redisConsumerDelegate);
    }

    @ConditionalOnBean(RedisConsumer.class)
    @Bean
    public RedisConsumerDelegate redisConsumerDelegate(List<RedisConsumer> redisConsumerList) {
        return new RedisConsumerDelegate(redisConsumerList);
    }

}
