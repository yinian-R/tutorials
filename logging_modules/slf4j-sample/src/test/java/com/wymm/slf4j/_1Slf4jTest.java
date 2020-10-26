package com.wymm.slf4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Slf4j简单示例
 */
class _1Slf4jTest {
    
    private static Logger log = LoggerFactory.getLogger(_1Slf4jTest.class);
    
    @Test
    public void test() {
        log.debug("log message");
        log.info("log message");
        log.warn("log message");
        log.error("log message");
        
        log.info("log message:{}", "Hello World");
        
        
        // log exception
        log.error("an exception", new Exception("http request error"));
        log.error("{} {} an exception", "hello", "world", new Exception("http request error"));
    }
    
}