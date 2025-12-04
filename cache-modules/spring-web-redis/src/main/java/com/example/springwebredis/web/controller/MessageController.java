//package com.example.springwebredis.web.controller;
//
//import com.example.springwebredis.message.RedissonDelayQueue;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@Api(tags = "redis 消息")
//@RequestMapping("/redisMessage")
//@RestController
//public class MessageController {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Resource
//    private RedissonDelayQueue redissonDelayQueue;
//
//    @PostMapping("/sendMessage")
//    public void sendMessage(String channel, String message) {
//        stringRedisTemplate.convertAndSend(channel, message);
//    }
//
//    @GetMapping("/sendDelayMessage")
//    public void sendDelayMessage(@RequestParam("task") String task) {
//        redissonDelayQueue.offerTask(task, 5);
//    }
//
//}
