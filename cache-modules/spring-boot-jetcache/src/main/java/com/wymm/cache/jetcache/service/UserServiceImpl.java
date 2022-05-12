package com.wymm.cache.jetcache.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.wymm.cache.jetcache.model.User;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private List<User> userList = new ArrayList<>();
    
    @PostConstruct
    public void setup() {
        userList.add(User.builder().id(1L).name("A").age(1).build());
        userList.add(User.builder().id(2L).name("B").age(1).build());
        userList.add(User.builder().id(3L).name("C").age(1).build());
    }
    
    /**
     * 使用本地缓存
     *
     * @param id
     * @return
     */
    @Cached(cacheType = CacheType.LOCAL, name = "userCache.findById", postCondition = "#result != null")
    @Override
    public User findById(Long id) {
        log.info("findById:" + id);
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @Cached(cacheType = CacheType.LOCAL, name = "userCache.findByBoolean", postCondition = "#result != null && #result.size() > 0")
    @Override
    public List<User> findByBoolean(Boolean b) {
        log.info("findByBoolean:" + b);
        if (b == null) {
            return null;
        }
        if (b) {
            return userList;
        } else {
            return Collections.emptyList();
        }
    }
    
}
