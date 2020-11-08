package com.wymm.custom.mybatis.dao;

import com.wymm.custom.mybatis.entity.User;
import com.wymm.mybatis.io.Resources;
import com.wymm.mybatis.sqlSession.SqlSession;
import com.wymm.mybatis.sqlSession.SqlSessionFactory;
import com.wymm.mybatis.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> finds() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        
        return sqlSession.find("user.finds");
    }
    
    @Override
    public User findByName(User user) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        
        return sqlSession.find("user.findByName", user);
    }
}
