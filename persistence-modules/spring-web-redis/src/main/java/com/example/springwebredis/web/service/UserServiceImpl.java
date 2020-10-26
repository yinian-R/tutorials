package com.example.springwebredis.web.service;

import com.example.springwebredis.web.mapper.UserMapper;
import com.example.springwebredis.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserMapper userMapper;
    
    @Cacheable(value = "users")
    @Override
    public List<User> findUsers() {
        return userMapper.findUsers();
    }
    
    @Cacheable(cacheNames = "users")
    @Override
    public User findUser(String id) {
        return userMapper.getUser(id);
    }
    
    @CachePut(cacheNames = "users", key = "#user.id")
    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user);
    }
    
    @CacheEvict(cacheNames = "users")
    @Override
    public boolean deleteUser(String id) {
        return userMapper.deleteUser(id);
    }
}
