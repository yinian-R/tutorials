package com.wymm.mybatis.sqlSession;

import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    <E> List<E> finds(String statementId, Object... params) throws SQLException;

    <E> E find(String statementId, Object... params) throws SQLException;
}
