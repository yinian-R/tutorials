package com.wymm.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jExample {
    static Logger logger = LoggerFactory.getLogger(Slf4jExample.class);
    
    public static void main(String[] args) {
        logger.info("log message");
        logger.debug("log message");
        logger.warn("log message");
        logger.error("log message");
    
        logger.info("log message:{}", "Hello World");
        
        
        // log exception
        logger.error("an exception", new Exception("http request error"));
        logger.error("{} {} an exception", "hello", "world", new Exception("http request error"));
    }
    
}
