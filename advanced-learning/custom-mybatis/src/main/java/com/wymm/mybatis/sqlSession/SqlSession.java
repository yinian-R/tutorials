package com.wymm.mybatis.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    <E> List<E> finds(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IntrospectionException, InstantiationException;
    
    <E> E find(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IntrospectionException, InvocationTargetException;
}
