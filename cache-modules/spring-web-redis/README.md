引入依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

更多功能的依赖，例如延迟队列
引入它后 spring cache 也会使用redisson，导致需要手动注入Bean：CacheManager，并且 spring.cache.redis 的配置无效
> redisson-spring-boot-starter 包含 spring-boot-starter-data-redis
```
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson-spring-boot-starter</artifactId>
            <version>3.17.7</version>
        </dependency>
```

使用注意事项：
> 目前配置 Spring Cache、RedisTemplate<String, Object> 设置Value全是不是 json 结构，并且包含类 @class 信息。
若需要缓存对象为 json，并且不包含类 @class 信息的话，请使用 StringRedisTemplate。或者在注入一个使用 jackson2JsonRedisSerializer 的 RedisTemplate<String, Object> ，如代码中的 pureJsonRedisTemplate


功能清单：
1. 注册 Bean：KeyGenerator 自定义缓存生成的key
2. spring cache condition使用示例
3. spring cache 增删改查
4.          //修改特定缓存的配置
            builder.withCacheConfiguration("specificCacheName",config);
            // 修改默认配置
            builder.cacheDefaults(config);