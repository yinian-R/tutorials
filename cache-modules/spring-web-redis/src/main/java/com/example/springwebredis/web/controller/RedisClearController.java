package com.example.springwebredis.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "Redis 缓存清除")
@RestController
@RequestMapping("/redisClear")
public class RedisClearController {
    
    @Resource
    private CacheManager cacheManager;
    
    @ApiOperation("清除所有Spring Cache缓存")
    @PostMapping("/clearAllCachesProgrammatically")
    public void clearAllCachesProgrammatically() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        });
    }
    
    
}
