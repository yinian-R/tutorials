package com.wymm.concurrent.base2;

import com.wymm.concurrent.base2.stopping.ControlSubThread;
import org.junit.jupiter.api.Test;

/**
 * 介绍如何在 Java 中停止 Thread
 */
class _3StopThreadTest {
    
    /**
     * 当sleep（）设置为较长的间隔时，或者我们正在等待可能永远不会释放的锁时，会发生什么？
     * 我们面临长期阻塞或永不干净终止的风险。
     *
     * 如果在调用线程时该线程正在休眠，则 sleep() 将以 InterruptedException 退出，其他任何阻塞调用也将退出
     */
    @Test
    void stopThread() throws InterruptedException {
        ControlSubThread controlSubThread = new ControlSubThread(10000);
        controlSubThread.start();
        Thread.sleep(1000);
        controlSubThread.interrupt();
    }
    
}
