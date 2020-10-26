package com.wymm.log4j.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogAdapterTest {
    
    @Test
    public void test() {
        Log log = LogAdapter.getLog("hello");
        log.info("hello word");
    }
    
}