package com.wymm.springbootsimplejdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的SQL操作
 */
@Slf4j
@Repository
public class FooDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;
    
    public void insertData() {
        Arrays.asList("a", "b").forEach(bar -> {
            jdbcTemplate.update("insert into FOO(BAR) values (?)", bar);
        });
        
        Map<String, String> row = new HashMap<>();
        row.put("BAR", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of d:{}", id);
    }
    
    public void listData() {
        log.info("Count:{}", jdbcTemplate.queryForObject("select count(1) from FOO", Long.class));
        
        jdbcTemplate.queryForList("select BAR from FOO")
                .forEach(bar -> log.info("BAR:{}", bar));
        
        jdbcTemplate.query("select * from FOO",
                (resultSet, i) -> Foo.builder()
                        .id(resultSet.getLong(1))
                        .bar(resultSet.getString(2))
                        .build()
        ).forEach(foo -> log.info("Foo:{}", foo));
    }
}
