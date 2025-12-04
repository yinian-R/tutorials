package com.example.springwebredis.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.Arrays;

@Configuration
public class SpringCacheConfig {
    
    /**
     * 设置 JSON 序列化器，并且保留 spring.cache.redis 配置生效
     *
     * @param cacheProperties
     * @return
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(CacheProperties cacheProperties,
                                                                                 GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer) {
        return builder -> {
            CacheProperties.Redis redisProperties = cacheProperties.getRedis();
            
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer));
            if (redisProperties.getTimeToLive() != null) {
                config = config.entryTtl(redisProperties.getTimeToLive());
            }
            if (redisProperties.getKeyPrefix() != null) {
                config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
            }
            if (!redisProperties.isCacheNullValues()) {
                config = config.disableCachingNullValues();
            }
            if (!redisProperties.isUseKeyPrefix()) {
                config = config.disableKeyPrefix();
            }
            
            //修改特定缓存的配置
            //builder.withCacheConfiguration("specificCacheName",config);
            // 修改默认配置
            builder.cacheDefaults(config);
        };
    }
    
    /**
     * 自定义缓存key的方法
     * 使用示例：@Cacheable(cacheNames = "users", keyGenerator = "customKeyGenerator")
     *
     * @return
     */
    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> target.getClass().getSimpleName() + "_" + method.getName() + "_" + Arrays.toString(params);
    }
    
}
