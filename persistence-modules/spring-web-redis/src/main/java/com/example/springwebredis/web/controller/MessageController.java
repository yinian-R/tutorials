package com.example.springwebredis.web.controller;

import com.example.springwebredis.message.RedissonDelayQueue;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/message")
@RestController
public class MessageController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonDelayQueue redissonDelayQueue;

    @ApiOperation("发送消息")
    @PostMapping("/sendMessage")
    public void sendMessage(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }


}
