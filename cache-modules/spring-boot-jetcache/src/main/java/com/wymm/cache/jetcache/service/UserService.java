package com.wymm.cache.jetcache.service;

import com.wymm.cache.jetcache.model.User;

import java.util.List;

public interface UserService {
    
    User findById(Long id);
    
    
    List<User> findByBoolean(Boolean b);
    
}
