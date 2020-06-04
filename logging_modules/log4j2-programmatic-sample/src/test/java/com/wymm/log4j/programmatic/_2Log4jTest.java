package com.wymm.log4j.programmatic;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Test;

@Log4j2
public class _2Log4jTest extends Parent1 {
	
	/**
	 * 使用lombok引入log，配置必须先配置好
	 */
	@Test
	public void useLombok_thenPrint() {
		log.trace("log message");
		log.debug("log message");
		log.info("log message");
		log.warn("log message");
		log.error("log message", new Exception("test error"));
	}
	
	/**
	 * 测试压缩文件
	 */
	@Test
	public void rollingFile_thenPrint() {
		for (int i = 0; i < 10000; i++) {
			log.trace("log message");
			log.debug("log message");
			log.info("log message");
			log.warn("log message");
			log.error("log message", new Exception("test error"));
		}
	}
	
}

class Parent1 {
	static {
		Configurator.initialize(CustomConfigurationBuilder.configure().build());
	}
}