package com.wymm.mybatis.sqlSession;

import com.wymm.mybatis.pojo.Configuration;
import com.wymm.mybatis.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
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
    
    /**
     * 使用 JDK 动态代理来为 Dao 接口生成代理对象，并返回
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object object = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            // 底层还是去执行 JDBC 代码
            // 根据不同情况调用 selectList 或 selectOne
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String statementId = className + "." + methodName;
            
            // 获取被调用方法返回值类型
            Type genericReturnType = method.getGenericReturnType();
            // 判断是否进行了泛型类型参数化
            if (genericReturnType instanceof ParameterizedType) {
                return finds(statementId, args);
            } else {
                return find(statementId, args);
            }
        });
        return (T) object;
    }
}
