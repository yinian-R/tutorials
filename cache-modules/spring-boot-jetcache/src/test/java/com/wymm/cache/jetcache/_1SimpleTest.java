package com.wymm.cache.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.wymm.cache.jetcache.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * jetcache 简单示例
 */
@SpringBootTest
public class _1SimpleTest {
    
    /**
     * 通过 @CreateCache 注解创建一个缓存实例，超时时间是5秒
     */
    @CreateCache(expire = 5)
    private Cache<Long, User> userCache;
    
    @Test
    void simple() throws InterruptedException {
        User user = User.builder().id(1L).name("book").age(1).build();
        // 设置缓存
        userCache.put(1L, user);
        // 获取缓存
        User cacheItem = userCache.get(1L);
        System.out.println(cacheItem);
    
        Thread.sleep(5000);
    
        cacheItem = userCache.get(1L);
        System.out.println(cacheItem);
        
        // 删除缓存
        userCache.remove(1L);
    }
    
}
