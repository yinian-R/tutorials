package com.wymm.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Logback使用slf4j作为接口，因此引入slf4j的Logger和LoggerFactory
 */
@Slf4j
public class _1LogbackTest {

    /**
     * 使用 slf4j 简单的日志输出
     */
    @Test
    public void test() {
        log.trace("log message");
        log.debug("log message");
        log.info("log message");
        log.warn("log message");
        log.error("log message", new Exception("test error"));
    }

    /**
     * 使用编程方式设置日志级别
     */
    @Test
    public void userProgramme_setLevel() {
        Logger parentLogger = (Logger) LoggerFactory.getLogger("com.wymm.logback");
        parentLogger.setLevel(Level.INFO);

        Logger childLogger = (Logger) LoggerFactory.getLogger("com.wymm.logback.tests");

        parentLogger.debug("This message is not logged because DEBUG < INFO.");
        parentLogger.warn("This message is logged because WARN > INFO.");
        childLogger.debug("DEBUG < INFO");
        childLogger.info("INFO == INFO");
    }


    /**
     * 使用配置设置日志级别
     */
    @Test
    public void userFileConfig_setLevel() {
        Logger parentLogger = (Logger) LoggerFactory.getLogger("com.wymm.logback");
        Logger childLogger = (Logger) LoggerFactory.getLogger("com.wymm.logback.level");

        parentLogger.info("parentLogger");

        childLogger.trace("logger message");
        childLogger.debug("logger message");
        childLogger.info("logger message");
        childLogger.warn("logger message");
        childLogger.error("logger message", new Exception("test error"));
    }

    @Test
    public void setRootLevel() {
        Logger logger = (Logger) LoggerFactory.getLogger("com.wymm.logback");
        logger.debug("Hi there!");

        Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        logger.debug("This message is logged because DEBUG == DEBUG.");

        rootLogger.setLevel(Level.ERROR);

        logger.warn("This message is not logged because WARN < ERROR.");
        logger.error("This is logged.");
    }

}
