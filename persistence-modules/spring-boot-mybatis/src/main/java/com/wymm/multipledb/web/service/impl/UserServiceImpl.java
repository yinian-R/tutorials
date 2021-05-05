package com.wymm.multipledb.web.service.impl;

import com.wymm.multipledb.web.model.User;
import com.wymm.multipledb.web.repository.mysql.UserMapper;
import com.wymm.multipledb.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//@Service
public class UserServiceImpl implements UserService {
    
    /**
     * 使用 @Resource 编译器不会报：Could not autowire. No beans of 'UserMapper' type found.
     */
    @Resource
    private UserMapper userMapper;
    
    @Override
    public List<User> finds() {
        return userMapper.finds();
    }
}
