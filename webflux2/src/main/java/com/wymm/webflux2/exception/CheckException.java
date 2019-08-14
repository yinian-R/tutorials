package com.wymm.webflux2.exception;

import lombok.Data;

@Data
public class CheckException extends RuntimeException {

    // 出错字段的名称
    private String fieldName;

    // 出错字段的值
    private String filedValue;

    public CheckException(String fieldName, String filedValue) {
        super();
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

}
