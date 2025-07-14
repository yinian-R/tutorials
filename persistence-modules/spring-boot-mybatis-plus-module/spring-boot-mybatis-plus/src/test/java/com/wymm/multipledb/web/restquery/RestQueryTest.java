package com.wymm.multipledb.web.restquery;

import com.wymm.multipledb.web.entity.User;
import com.wymm.multipledb.web.mapper.mysql.BookMapper;
import com.wymm.multipledb.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RestQueryTest {
    
    @Autowired
    BookMapper bookMapper;
    @Autowired
    UserService userService;
    
    @Test
    public void test() {
        //userService.ktQuery()
        //        .eq(User::getFirst_name, "John");
        User user = new User();
        user.setId(100L);
        user.setFirstName("1");
        user.setLastName("2");
        user.setEmail("3");
        user.setAge("4");
        
        userService.getBaseMapper().insert(user);
    }
    
}
