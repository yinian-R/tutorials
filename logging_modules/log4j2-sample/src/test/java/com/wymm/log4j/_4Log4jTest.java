package com.wymm.log4j;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Random;

@Log4j2
public class _4Log4jTest {
    
    /**
     * 使用lambda表达式进行懒记录
     */
    @Test
    public void lambda() {
        // bad
        log.debug("Number is {}", randomInt());
        
        // good
        if (log.isDebugEnabled()) {
            log.debug("Number is {}", randomInt());
        }
        
        // better
        log.debug("Number is {}", this::randomInt);
    }
    
    private int randomInt() {
        Random random = new Random();
        return random.nextInt();
    }
    
    
    @Test
    public void test() {
        
    }
}
