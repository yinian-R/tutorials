package com.wymm.multipledb.web.mapper.mysql2;

import com.wymm.multipledb.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    
    @Test
    void test(){
        User user = new User();
        user.setId(0L);
        user.setFirstName("F");
        user.setLastName("A");
        user.setEmail("@");
        user.setAge("12");
        
        userMapper.insert(user);
    }
    
}