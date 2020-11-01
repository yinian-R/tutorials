package com.wymm.mybatis.config;

import com.wymm.mybatis.utils.ParameterMapping;
import lombok.Getter;

import java.util.List;

@Getter
public class BoundSql {
    
    private String sqlText; // 解析后的 SQL
    
    private List<ParameterMapping> parameterMappingList;
    
    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }
}
