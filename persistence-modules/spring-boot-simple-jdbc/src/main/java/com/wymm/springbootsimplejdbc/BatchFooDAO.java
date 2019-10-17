package com.wymm.springbootsimplejdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class BatchFooDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    public void batchInsetData() {
        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().bar("b-1").build());
        list.add(Foo.builder().bar("b-2").build());
        jdbcTemplate.batchUpdate("insert into FOO(BAR) values (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, list.get(i).getBar());
            }
            
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        
        List<Foo> list100 = new ArrayList<>();
        list100.add(Foo.builder().bar("b-100").build());
        list100.add(Foo.builder().bar("b-101").build());
        namedParameterJdbcTemplate.batchUpdate("insert into FOO(ID, BAR) values (:id, :bar)",
                SqlParameterSourceUtils.createBatch(list100));
    }
    
}
