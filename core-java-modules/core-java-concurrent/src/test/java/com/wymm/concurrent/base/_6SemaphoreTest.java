package com.wymm.concurrent.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

/**
 * todo not finish
 */
@Slf4j
class _6SemaphoreTest {
    
    private static Semaphore semaphore = new Semaphore(10);
    
    @Test
    void execute() {
        log.info("Available permit:" + semaphore.availablePermits());
        log.info("Number of threads waiting to acquire: " + semaphore.getQueueLength());
        if (semaphore.tryAcquire()) {
            try {
                log.info(" running ");
            } catch (Exception e) {
                semaphore.release();
            }
        }
    }
    
}
