package com.wymm.concurrent.base2;

import com.wymm.concurrent.base2.join.SampleThread;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
class _7JoinTest {
    
    /**
     * join()
     * 当我们在调用线程上调用 join() 时，调用线程进入等待状态，它保持等待状态，直到引用的线程终止
     *
     * 如果引用的线程被中断，join() 方法也可能返回。在这种情况下，该方法将抛出 InterruptedException。
     * 如果所引用的线程已经终止或尚未启动，则对 join() 方法的调用将立即返回。
     */
    @Test
    public void givenStartedThread_whenJoinCalled_waitsTillCompletion()
            throws InterruptedException {
        Thread t2 = new SampleThread(1);
        t2.start();
        log.info("Invoking join");
        t2.join();
        // 最多等待 1000 毫秒
        //t2.join(1000);
        log.info("Returned from join");
        assertFalse(t2.isAlive());
    }
}
