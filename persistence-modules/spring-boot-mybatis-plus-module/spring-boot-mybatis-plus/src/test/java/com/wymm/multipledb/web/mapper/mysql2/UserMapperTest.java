package com.wymm.multipledb.web.mapper.mysql2;

import com.wymm.multipledb.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    
    @Test
    void test(){
        User user = new User();
        user.setId(0L);
        user.setFirst_name("F");
        user.setLast_name("A");
        user.setEmail("@");
        user.setAge("12");
        
        userMapper.insert(user);
    }
    
}