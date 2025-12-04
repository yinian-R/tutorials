package com.wymm.cache.jetcache.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.wymm.cache.jetcache.model.User;
import com.wymm.cache.jetcache.model.qo.UserQO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    
    
    List<User> findPostCondition(Boolean b);
    
    List<User> findCondition(LocalDate date);
    
    List<User> findQO(UserQO qo);
}
