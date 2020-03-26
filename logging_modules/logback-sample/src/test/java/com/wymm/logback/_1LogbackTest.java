package com.wymm.logback;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Logback使用slf4j作为接口，因此引入slf4j的Logger和LoggerFactory
 */
@Slf4j
public class _1LogbackTest {
	
	@Test
	public void test() {
		log.trace("log message");
		log.debug("log message");
		log.info("log message");
		log.warn("log message");
		log.error("log message", new Exception("test error"));
	}
	
}
