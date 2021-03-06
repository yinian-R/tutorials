package com.wymm.mybatis.sqlSession;

import com.wymm.mybatis.pojo.Configuration;
import com.wymm.mybatis.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    
    private Configuration configuration;
    
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }
    
    @Override
    public <E> List<E> finds(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IntrospectionException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.find(configuration, mappedStatement, params);
        return (List<E>) list;
    }
    
    @Override
    public <E> E find(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IntrospectionException, InvocationTargetException {
        List<Object> objects = finds(statementId, params);
        if (objects.size() == 1) {
            return (E) objects.get(0);
        } else {
            throw new RuntimeException("查询结果期望：1，返回：" + objects.size());
        }
    }
}
