package com.wymm.mybatis;

import com.wymm.mybatis.io.Resources;
import com.wymm.mybatis.sqlSession.SqlSessionFactory;
import com.wymm.mybatis.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

class MybatisTest {
    @Test
    void test() throws DocumentException, PropertyVetoException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
}
