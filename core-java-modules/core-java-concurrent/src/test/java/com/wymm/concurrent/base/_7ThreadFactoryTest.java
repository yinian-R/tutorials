package com.wymm.concurrent.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ThreadFactory 充当线程（不存在）的池，可按需创建线程。它消除了实现高效线程创建机制所需的大量样板代码
 */
class _7ThreadFactoryTest {
    
    @Test
    void test() throws InterruptedException {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("CustomThreadFactory");
        Thread thread = customThreadFactory.newThread(_7ThreadFactoryTest::print);
        thread.start();
        assertEquals("CustomThreadFactory-pool-1-thread-1", thread.getName());
    }
    
    static void print() {
        System.out.println("start print");
    }
}
