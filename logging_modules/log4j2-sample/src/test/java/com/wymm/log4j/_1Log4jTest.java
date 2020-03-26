package com.wymm.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * 使用Log4j简单示例
 */
public class _1Log4jTest {
	
	
	@Test
	public void test() {
		Logger log = LogManager.getLogger(_1Log4jTest.class);
		log.trace("log message");
		log.debug("log message");
		log.info("log message");
		log.warn("log message");
		log.error("log message", new Exception("test error"));
		log.fatal("log message", new Exception("test error"));
	}
}
