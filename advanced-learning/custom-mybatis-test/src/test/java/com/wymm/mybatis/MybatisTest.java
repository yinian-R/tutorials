package com.wymm.mybatis;

import com.wymm.custom.mybatis.dao.UserDAO;
import com.wymm.custom.mybatis.entity.User;
import com.wymm.mybatis.io.Resources;
import com.wymm.mybatis.sqlSession.SqlSession;
import com.wymm.mybatis.sqlSession.SqlSessionFactory;
import com.wymm.mybatis.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

class MybatisTest {
    
    @Test
    void test() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        
        User user = new User();
        user.setId(1);
        user.setName("xiaohui");
    
        UserDAO userDAO = sqlSession.getMapper(UserDAO.class);
        User user1 = userDAO.findByName(user);
        System.out.println(user1);
    }
}
