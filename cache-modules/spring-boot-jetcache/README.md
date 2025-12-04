## jecache 
https://github.com/alibaba/jetcache/wiki/Home_CN

## 引入依赖
```
<!-- JetCache -->
<dependency>
    <groupId>com.alicp.jetcache</groupId>
    <artifactId>jetcache-starter-redis</artifactId>
    <version>2.6.4</version>
</dependency>
```

## 启动类添加注解
```
@EnableMethodCache(basePackages = "com.wymm.cache.jetcache")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class JetCacheApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JetCacheApplication.class, args);
    }
    
}
```

## yml 配置有些难度
```
JetCacheAutoConfiguration

RedisAutoConfiguration

CaffeineAutoConfiguration


@ConfigurationProperties(prefix = "jetcache")
public class JetCacheProperties
```

## 参考
官网文档（包含配置和注解说明等） https://github.com/alibaba/jetcache/tree/master/docs/CN
