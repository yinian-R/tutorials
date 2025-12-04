package com.wymm.cache.jetcache.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.wymm.cache.jetcache.model.User;

import com.wymm.cache.jetcache.model.qo.UserQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private List<User> userList = new ArrayList<>();
    
    /**
     * 根据返回值判断是否使用缓存
     * @param b
     * @return
     */
    @Cached(cacheType = CacheType.LOCAL, name = "userCache.findPostCondition", postCondition = "#result != null && #result.size() > 0")
    @Override
    public List<User> findPostCondition(Boolean b) {
        log.info("findPostCondition:" + b);
        if (b == null) {
            return null;
        }
        if (b) {
            return userList;
        } else {
            return Collections.emptyList();
        }
    }
    
    /**
     * 在执行实际方法前判断是否使用缓存
     * @param date
     * @return
     */
    @Cached(cacheType = CacheType.LOCAL, name = "userCache.findCondition", condition = "#date.isBefore(T(java.time.LocalDate).now())")
    @Override
    public List<User> findCondition(LocalDate date) {
        log.info("findCondition:" + date);
        return userList;
    }
    @Cached(cacheType = CacheType.LOCAL, name = "userCache.findQO", condition = "#qo.date.isBefore(T(java.time.LocalDate).now())")
    @Override
    public List<User> findQO(UserQO qo) {
        log.info("findQO:" + qo);
        return userList;
    }
    
}
