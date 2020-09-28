package com.wymm.mybatis.sqlSession;

import com.wymm.mybatis.pojo.Configuration;
import com.wymm.mybatis.pojo.MappedStatement;

import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> find(Configuration configuration, MappedStatement mappedStatement, Object... params) {
        return null;
    }
}
