package com.wymm.webflux.core.advice;

import com.wymm.webflux.exception.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class CheckAdvice {
    
    /**
     * 把校验异常转换为字符串
     *
     * @param e WebExchangeBindException
     * @return WebExchangeBindException String
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException e) {
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }
    
    private String toStr(WebExchangeBindException e) {
        return e.getFieldErrors().stream()
                .map(ex -> ex.getField() + ":" + ex.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
    
    @ExceptionHandler(CheckException.class)
    public ResponseEntity handleBindException(CheckException e) {
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }
    
    private String toStr(CheckException e) {
        return e.getFieldName() + ":错误的值 " + e.getFiledValue();
    }
}
