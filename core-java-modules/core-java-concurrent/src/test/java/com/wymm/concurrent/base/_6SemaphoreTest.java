package com.wymm.concurrent.base;

import com.wymm.concurrent.base.semaphore.DelayQueueUsingTimedSemaphore;
import com.wymm.concurrent.base.semaphore.LoginQueueUsingSemaphore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo not finish
 */
@Slf4j
class _6SemaphoreTest {
    
    /**
     * Semaphore 简单示例
     */
    @Test
    void example() {
        Semaphore semaphore = new Semaphore(10);
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
    
    /**
     * 实现一个简单的登录队列以限制系统中的用户数
     */
    @Test
    void givenLoginQueue_whenReachLimit_thenBlocked() throws InterruptedException {
        int slots = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        IntStream.range(0, slots).forEach(v -> executorService.submit(loginQueue::tryLogin));
        
        Thread.sleep(10);
        executorService.shutdown();
        
        // 测试我们的登录队列，我们将首先尝试达到限制，并检查是否将阻止下次登录尝试
        assertEquals(0, loginQueue.availableSlots());
        assertFalse(loginQueue.tryLogin());
        
        // 查看 logout 后是否有可用的插槽
        loginQueue.logout();
        assertTrue(loginQueue.availableSlots() > 0);
        assertTrue(loginQueue.tryLogin());
    }
    
    /**
     * Apache Commons TimedSemaphore 允许多个许可证作为一个简单的信号量，但是在给定的时间段内，在这个时间段之后，时间重置并释放所有许可证。
     */
    @Test
    void givenDelayQueue_whenReachLimit_thenBlocked() throws InterruptedException {
        int slots = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);
        
        IntStream.range(0, slots).forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();
        
        // 当我们使用一个以1秒为时间段的延迟队列，并且在一秒钟内使用完所有插槽
        assertEquals(0, delayQueue.availableSlots());
        assertFalse(delayQueue.tryAdd());
        
        // 经过一段时间的睡眠后，信号量应该重置并释放许可证
        Thread.sleep(1000);
        assertTrue(delayQueue.availableSlots() > 0);
        assertTrue(delayQueue.tryAdd());
    }
    
}
