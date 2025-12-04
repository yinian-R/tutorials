package com.example.springwebredis.web.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api(tags = "RedisTemplate 示例")
@RestController
@RequestMapping("/redisTemplate")
public class RedisTemplateController {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        return "Set key '" + key + "' with value '" + value + "'";
    }
    
    @GetMapping("/get")
    public Object getValue(@RequestParam String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object value = operations.get(key);
        return value != null ? value : "Key not found";
    }
    
    @DeleteMapping("/delete")
    public String deleteKey(@RequestParam String key) {
        Boolean deleted = redisTemplate.delete(key);
        return deleted ? "Deleted key '" + key + "'" : "Key '" + key + "' not found";
    }
    
    @PostMapping("/setWithTTL")
    public String setValueWithTTL(@RequestParam String key,
                                  @RequestParam String value,
                                  @RequestParam long ttl) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value, ttl, TimeUnit.SECONDS);
        return "Set key '" + key + "' with value '" + value + "' and TTL " + ttl + " seconds";
    }
    
}
