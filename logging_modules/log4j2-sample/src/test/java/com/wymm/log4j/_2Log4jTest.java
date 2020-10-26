package com.wymm.log4j;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;


/**
 * Log4j和Lombok结合使用
 */
@Log4j2
public class _2Log4jTest {
    
    @Test
    public void test() {
        log.trace("log message");
        log.debug("log message");
        log.info("log message");
        log.warn("log message");
        log.error("log message", new Exception("test error"));
        log.fatal("log message", new Exception("test error"));
    }
}
