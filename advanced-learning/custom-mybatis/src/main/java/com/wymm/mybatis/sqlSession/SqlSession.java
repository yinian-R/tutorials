package com.wymm.mybatis.sqlSession;

import java.util.List;

public interface SqlSession {
    <E> List<E> finds(String statementId, Object... params);

    <E> E find(String statementId, Object... params);
}
