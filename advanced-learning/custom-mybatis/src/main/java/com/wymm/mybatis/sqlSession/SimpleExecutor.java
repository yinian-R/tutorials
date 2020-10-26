package com.wymm.mybatis.sqlSession;

import com.wymm.mybatis.config.BoundSql;
import com.wymm.mybatis.pojo.Configuration;
import com.wymm.mybatis.pojo.MappedStatement;
import com.wymm.mybatis.utils.GenericTokenParser;
import com.wymm.mybatis.utils.ParameterMapping;
import com.wymm.mybatis.utils.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> find(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.获取 SQL :select * from user where name=#{name}
        //   转换 SQL :select * from user where name=? 转换过程中还需要对 #{} 中的值进行解析存储

        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4.设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            // 反射
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[i]);

            preparedStatement.setObject(i + 1, o);
        }

        // 5.执行 SQL

        // 6.封装返回结果集
        return null;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            return Class.forName(paramterType);
        }
        return null;
    }

    /**
     * 完成对 #{} 的解析工作：1.将 #{} 使用 ? 替换，2.解析出 #{} 里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        // 标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        // 解析出来的 SQL
        String parseSql = genericTokenParser.parse(sql);
        // #{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        return new BoundSql(parseSql, parameterMappings);
    }
}
