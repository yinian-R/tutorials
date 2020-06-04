package com.wymm.log4j;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class _3Slf4jTest {
	@Test
	public void useSlf4j() {
		log.trace("log message");
		log.debug("log message");
		log.info("log message");
		log.warn("log message");
		log.error("log message", new Exception("test error"));
	}
}
