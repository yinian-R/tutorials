package com.wymm.mybatis.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Configuration {
    
    private DataSource dataSource;
    
    /**
     * key: namespace.id
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
    
}
