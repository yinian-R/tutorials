package com.wymm.log4j.programmatic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class _1Log4jTest {
    
    /**
     * 打印编程配置
     */
    @Test
    public void toXmlConfiguration_thenPrintln() {
        CustomConfigurationBuilder factory = new CustomConfigurationBuilder();
        System.out.println(factory.configure().toXmlConfiguration());
    }
    
    /**
     * 未配置Log4j，使用log4j
     */
    @Test
    public void log4jPrint() {
        Logger log = LogManager.getLogger(_1Log4jTest.class);
        log.trace("log message");
        log.debug("log message");
        log.info("log message");
        log.warn("log message");
        log.error("log message", new Exception("test error"));
        log.fatal("log message", new Exception("test error"));
    }
    
    /**
     * 未配置Log4j，使用slf4j
     */
    @Test
    public void slf4jPrint() {
        org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(_1Log4jTest.class);
        log.trace("log message");
        log.debug("log message");
        log.info("log message");
        log.warn("log message");
        log.error("log message", new Exception("test error"));
    }
    
    @Test
    public void slf4jPrint2() {
        org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(_1Log4jTest.class);
        log.error("log message");
    }
}
