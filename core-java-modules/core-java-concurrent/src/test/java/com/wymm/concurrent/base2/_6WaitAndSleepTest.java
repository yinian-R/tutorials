package com.wymm.concurrent.base2;

import com.wymm.concurrent.base2.waitsleep.ThreadA;
import org.junit.jupiter.api.Test;

/**
 * wait() 是用于线程同步的实例方法。可以在任何对象上调用它，因为它是在 java.lang.Object，但是只能从同步块中调用它。它释放对象上的锁，以便另一个线程可以进入并获取锁
 * sleep() 是一个静态方法，可以在任何上下文调用。Thread.sleep() 暂停当前线程，并不释放任何锁
 */
class _6WaitAndSleepTest {
    
    private static final Object LOCK = new Object();
    
    /**
     * sleep 1秒后唤醒线程 main
     * wait 1秒后唤醒 LOCK 对象
     */
    @Test
    void sleepWaitExamples() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(
                "Thread '" + Thread.currentThread().getName() +
                        "' is woken after sleeping for 1 second");
        
        synchronized (LOCK) {
            LOCK.wait(1000);
            System.out.println("Object '" + LOCK + "' is woken after" +
                    " waiting for 1 second");
        }
    }
    
    /**
     * wait() 和 notify() 示例
     * 1. wait 线程 ThreadB 完成
     * 2. ThreadB 完成，输出：Sum from that thread is: 704982704
     */
    @Test
    void usingWaitAndNotify() throws InterruptedException {
        ThreadA threadA = new ThreadA();
        threadA.run();
    }
}
