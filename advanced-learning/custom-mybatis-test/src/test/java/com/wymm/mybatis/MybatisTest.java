package com.wymm.mybatis;

import com.wymm.custom.mybatis.User;
import com.wymm.mybatis.io.Resources;
import com.wymm.mybatis.sqlSession.SqlSession;
import com.wymm.mybatis.sqlSession.SqlSessionFactory;
import com.wymm.mybatis.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

class MybatisTest {
    @Test
    void test() throws DocumentException, PropertyVetoException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(1);
        user.setUsername("xiaohui");
        Object user1 = sqlSession.find("user.findUserByName", user);
    }
}
