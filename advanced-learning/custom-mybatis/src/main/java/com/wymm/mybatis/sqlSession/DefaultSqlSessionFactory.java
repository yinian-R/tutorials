package com.wymm.mybatis.sqlSession;

import com.wymm.mybatis.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;
    
    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }
    
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
