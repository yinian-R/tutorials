package com.wymm.mybatis.sqlSession;

import com.wymm.mybatis.config.XMLConfigBuilder;
import com.wymm.mybatis.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException {
        // 使用 dom4j 解析配置文件，将解析出来的内容封装到 Configuration 中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        // 创建 sqlSessionFactory 对象：工厂类：生产 sqlSession 会话对象
        SqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(configuration);

        return sessionFactory;
    }
}
