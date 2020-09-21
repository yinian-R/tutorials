package com.wymm.mybatis.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MappedStatement {

    /**
     * id 标识
     */
    private String id;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * 参数值类型
     */
    private String paramterType;

    /**
     * SQL 语句
     */
    private String sql;

}
