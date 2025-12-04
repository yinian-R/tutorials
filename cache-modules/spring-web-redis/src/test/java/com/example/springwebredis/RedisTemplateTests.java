package com.example.springwebredis;

import com.example.springwebredis.web.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTemplateTests {
    
    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    @Test
    public void testString() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
        valueOperations.set("redis:str:user", "huang");
        Assertions.assertEquals(valueOperations.get("redis:str:user"), "huang");
    }
    
    @Test
    public void testObj() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
        User user = new User(9L, "yinian");
        
        valueOperations.set("redis:obj:user", user);
        Object result = valueOperations.get("redis:obj:user");
        Assertions.assertEquals(result, user);
    }
    
    /**
     * Hash(哈希)，是一个键值（key=>value）对集合
     * HK重复插入会覆盖
     */
    @Test
    public void testHash() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        
        User user1 = new User(9L, "yinian");
        User user2 = new User(10L, "luoli");
        
        hashOperations.put("redis:hash:user", "user1", user1);
        hashOperations.put("redis:hash:user", "user2", user2);
        
        Assertions.assertEquals(hashOperations.get("redis:hash:user", "user1"), user1);
    }
    
    /**
     * List(列表)
     * 按照插入顺序排序。左边是头部，右边是尾部
     * pop之后，值从列表中取出并移除
     */
    @Test
    public void testList() {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        User user1 = new User(1L, "yinian");
        User user2 = new User(2L, "luoli");
        
        listOperations.leftPush("redis:list:users", user1);
        listOperations.leftPush("redis:list:users", user2);
        
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        
        listOperations.leftPushAll("redis:list:usersBigList", users);
        
        Assertions.assertEquals(listOperations.leftPop("redis:list:users"), user2);
        Assertions.assertEquals(listOperations.leftPop("redis:list:users"), user1);
        
        System.out.println(listOperations.range("redis:list:usersBigList", 0, listOperations.size("list:usersBigList") - 1));
    }
    
    /**
     * Set(集合)
     * string无序集合，且不允许重复
     */
    @Test
    public void testSet() {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        
        User user1 = new User(9L, "yinian");
        User user2 = new User(10L, "luoli");
        setOperations.add("redis:set:user", user1, user2);
        
        Set<User> users = new LinkedHashSet<>();
        users.add(user1);
        users.add(user2);
        setOperations.add("redis:set:user2", users.toArray());
        
        System.out.println(setOperations.members("redis:set:user"));
        
        Assertions.assertEquals(setOperations.members("redis:set:user2"), users);
    }
    
    /**
     * ZSet(有序集合)
     * 与Set一样是string的集合，且不允许重复
     * 不同的是ZSet含有一个分数。redis正是通过分数为集合中的成员从小到大的排序
     */
    @Test
    public void testZSet() {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        User user1 = new User(9L, "yinian");
        User user2 = new User(10L, "luoli");
        zSetOperations.add("redis:zset:users", user1, 1);
        zSetOperations.add("redis:zset:users", user2, 0);
        
        System.out.println(zSetOperations.range("redis:zset:users", 0, 10));
    }
    
}
