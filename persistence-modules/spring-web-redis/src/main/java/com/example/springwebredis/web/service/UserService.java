package com.example.springwebredis.web.service;


import com.example.springwebredis.web.model.User;

import java.util.List;

public interface UserService {
    List<User> findUsers();
    
    User findUser(String id);
    
    boolean addUser(User user);
    
    boolean deleteUser(String id);
}
