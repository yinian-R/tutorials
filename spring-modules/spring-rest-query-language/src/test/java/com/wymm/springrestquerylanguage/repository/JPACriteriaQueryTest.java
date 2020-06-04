package com.wymm.springrestquerylanguage.repository;

import com.wymm.springrestquerylanguage.model.SearchCriteria;
import com.wymm.springrestquerylanguage.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JPACriteriaQueryTest {

    @Autowired
    private UserDAO userDAO;

    private static User userJohn;
    private static User userTom;

    @BeforeAll
    public static void setup() {
        userJohn = new User();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("jogn@doe.com");
        userJohn.setAge(24);

        userTom = new User();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(25);
    }

    /**
     * 添加两个用户初始化数据库用来支持测试
     */
    @Test
    public void test() {
        userDAO.save(userJohn);
        userDAO.save(userTom);
    }

    /**
     * 获取一个特定的 firstName 和 lastName 的用户
     */
    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect(){
        ArrayList<SearchCriteria> params = new ArrayList<>();
        params.add(new SearchCriteria("firstName", ":","John"));
        params.add(new SearchCriteria("lastName", ":","Doe"));

        List<User> users = userDAO.searchUser(params);
        assertTrue(users.contains(userJohn));
    }
}