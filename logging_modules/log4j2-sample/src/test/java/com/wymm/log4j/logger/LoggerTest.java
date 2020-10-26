package com.wymm.log4j.logger;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

/**
 * 测试配置文件<Logger>指定包下的日志配置是否生效
 */
@Log4j2
public class LoggerTest {
    
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
