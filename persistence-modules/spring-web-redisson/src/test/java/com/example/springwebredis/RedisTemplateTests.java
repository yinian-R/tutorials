//package com.example.springwebredis;
//
//import com.example.springwebredis.web.model.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.*;
//
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//@SpringBootTest
//public class RedisTemplateTests {
//
//    @Autowired
//    RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//
//    @Test
//    public void testString() {
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//
//        valueOperations.set("str:user", "huang");
//        Assertions.assertEquals(valueOperations.get("str:user"), "huang");
//    }
//
//    @Test
//    public void testObj() {
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//
//        User user = new User(9, "yinian");
//
//        valueOperations.set("obj:user", user);
//
//        Assertions.assertEquals(valueOperations.get("obj:user"), user);
//    }
//
//    /**
//     * Hash(哈希)，是一个键值（key=>value）对集合
//     * HK重复插入会覆盖
//     */
//    @Test
//    public void testHash() {
//        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
//
//        User user1 = new User(9, "yinian");
//        User user2 = new User(10, "luoli");
//
//        hashOperations.put("hash:user", "user1", user1);
//        hashOperations.put("hash:user", "user2", user2);
//
//        Assertions.assertEquals(hashOperations.get("hash:user", "user1"), user1);
//    }
//
//    /**
//     * List(列表)
//     * 按照插入顺序排序。左边是头部，右边是尾部
//     * pop之后，值从列表中取出并移除
//     */
//    @Test
//    public void testList() {
//        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
//        User user1 = new User(9, "yinian");
//        User user2 = new User(10, "luoli");
//
//        listOperations.leftPush("list:users", user1);
//        listOperations.leftPush("list:users", user2);
//
//        List<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//
//        listOperations.leftPushAll("list:users2", users);
//
//        Assertions.assertEquals(listOperations.leftPop("list:users"), user2);
//        Assertions.assertEquals(listOperations.leftPop("list:users"), user1);
//
//        System.out.println(listOperations.range("list:users2", 0, listOperations.size("list:user2") - 1));
//    }
//
//    /**
//     * Set(集合)
//     * string无序集合，且不允许重复
//     */
//    @Test
//    public void testSet() {
//        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
//
//        User user1 = new User(9, "yinian");
//        User user2 = new User(10, "luoli");
//        setOperations.add("set:user", user1, user2);
//
//        Set<User> users = new LinkedHashSet<>();
//        users.add(user1);
//        users.add(user2);
//        setOperations.add("set:user2", users.toArray());
//
//        System.out.println(setOperations.members("set:user"));
//
//        Assertions.assertEquals(setOperations.members("set:user2"), users);
//    }
//
//    /**
//     * ZSet(有序集合)
//     * 与Set一样是string的集合，且不允许重复
//     * 不同的是ZSet含有一个分数。redis正是通过分数为集合中的成员从小到大的排序
//     */
//    @Test
//    public void testZSet() {
//        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//        User user1 = new User(9, "yinian");
//        User user2 = new User(10, "luoli");
//        zSetOperations.add("zset:users", user1, 1);
//        zSetOperations.add("zset:users", user2, 0);
//
//        System.out.println(zSetOperations.range("zset:users", 0, 10));
//    }
//
//}
