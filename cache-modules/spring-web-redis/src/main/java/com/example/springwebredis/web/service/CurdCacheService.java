package com.example.springwebredis.web.service;


import com.example.springwebredis.web.model.User;
import com.example.springwebredis.web.model.qo.UserAddQO;

import java.util.List;

public interface CurdCacheService {
    List<User> findUsers();
    
    User getUser(Long id);
    
    User addUser(UserAddQO user);
    
    boolean deleteUser(Long id);
    
    User updateUser(User user);
    
    void clearAllCache( );
}
