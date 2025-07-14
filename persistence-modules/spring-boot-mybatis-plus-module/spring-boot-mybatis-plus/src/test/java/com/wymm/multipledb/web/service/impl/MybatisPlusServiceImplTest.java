package com.wymm.multipledb.web.service.impl;

import com.wymm.multipledb.web.entity.User;
import com.wymm.multipledb.web.service.BookService;
import com.wymm.multipledb.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusServiceImplTest {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @Test
    void test(){
        bookService.list().forEach(System.out::println);
    
        User user = new User();
        user.setId(0L);
        user.setFirstName("F");
        user.setLastName("A");
        user.setEmail("@");
        user.setAge("12");
    
        userService.save(user);
        userService.list(null).forEach(System.out::println);
    }
}