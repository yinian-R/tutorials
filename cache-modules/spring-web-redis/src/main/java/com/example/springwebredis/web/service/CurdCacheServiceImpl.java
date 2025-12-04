package com.example.springwebredis.web.service;

import cn.hutool.core.bean.BeanUtil;
import com.example.springwebredis.web.mapper.UserMapper;
import com.example.springwebredis.web.model.User;
import com.example.springwebredis.web.model.qo.UserAddQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@CacheConfig
@Service
@Transactional
public class CurdCacheServiceImpl implements CurdCacheService {
    
    @Autowired
    UserMapper userMapper;
    
    // 查询所有用户，通常不缓存列表，因为列表变化频繁
    @Override
    public List<User> findUsers() {
        return userMapper.findUsers();
    }
    
    // 查询单个用户，使用缓存
    @Cacheable(cacheNames = "users", key = "#id")
    @Override
    public User getUser(Long id) {
        return userMapper.getUser(id);
    }
    
    // 新增用户，不直接缓存，因为通常新增后需要查询才会缓存
    @Override
    public User addUser(UserAddQO qo) {
        User user = BeanUtil.toBean(qo, User.class);
        userMapper.addUser(user);
        return userMapper.getUser(user.getId());
    }
    
    // 更新用户，同时更新缓存
    @CachePut(cacheNames = "users", key = "#user.id")
    @Override
    public User updateUser(User user) {
        userMapper.updateUser(user);
        return userMapper.getUser(user.getId());
    }
    
    // 删除用户，同时从缓存中删除
    @CacheEvict(cacheNames = "users", key = "#id")
    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteUser(id);
    }
    
    // 清除所有用户缓存（例如在批量操作后）
    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void clearAllCache() {
        // 这个方法体可以空着，只是为了触发缓存清除
    }
}
