package com.wymm.slf4j;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Lombok和Slf4j结合使用
 */
@Slf4j
public class _2Slf4jTest {
	
	@Test
	public void useLombok() {
		log.trace("log message");
		log.debug("log message");
		log.info("log message");
		log.warn("log message");
		log.error("log message", new Exception("test error"));
	}
}
