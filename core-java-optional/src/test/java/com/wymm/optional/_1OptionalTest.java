package com.wymm.optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class _1OptionalTest {
    
    @Test
    public void get() {
        String str = "红色";
        // 正常输出：红色
        assertEquals(str, Optional.of(str));
    }
    
    /**
     * 当包装值不存在时，返回默认值
     */
    @Test
    public void thenOrElseAndOrElseGetDiffer() {
        String otherStr = "未知";
        String nullStr = null;
        
        // 异常：NullPointerException
        //log.info(Optional.of(nullStr).orElse(otherStr));
        
        // 输出其它
        assertEquals(otherStr, Optional.ofNullable(nullStr).orElse(otherStr));
        
        // 输出Supplier提供的值
        assertEquals("其它", Optional.ofNullable(nullStr).orElseGet(() -> "其它"));
        // 输出Supplier提供的值
        assertEquals(getDefault(), Optional.ofNullable(nullStr).orElseGet(this::getDefault));
    }
    
    private String getDefault() {
        return "default";
    }
    
    /**
     * 当包装值不存在时，不会返回默认值，而是会引发异常
     */
    @Test
    public void orElseThrow() {
        String nullStr = null;
        // 报Supplier提供的异常
        Optional.ofNullable(nullStr).orElseThrow(() -> new IllegalArgumentException("值不能为空"));
    }
    
    
    @Test
    public void orElseThrow2() {
        String nullStr = null;
        // 报Supplier提供的异常
        Optional.ofNullable(nullStr).orElseThrow(() -> new IllegalArgumentException("值不能为空"));
    }
    
    
}
