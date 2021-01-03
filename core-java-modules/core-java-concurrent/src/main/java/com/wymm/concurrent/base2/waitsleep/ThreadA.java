package com.wymm.concurrent.base2.waitsleep;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadA {
    
    final ThreadB b = new ThreadB();
    
    public void run() throws InterruptedException {
        b.start();
        synchronized (b) {
            while (b.sum == 0) {
                log.info("Waiting for ThreadB to complete");
                b.wait();
            }
            
            log.info("ThreadB has complete. Sum for that thread is:" + b.sum);
        }
    }
}
