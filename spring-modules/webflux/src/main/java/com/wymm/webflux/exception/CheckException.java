package com.wymm.webflux.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
