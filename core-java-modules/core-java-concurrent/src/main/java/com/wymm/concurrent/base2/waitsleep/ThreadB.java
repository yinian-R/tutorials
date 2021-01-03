package com.wymm.concurrent.base2.waitsleep;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ThreadB extends Thread {
    
    int sum;
    
    @Override
    public void run() {
        synchronized (this) {
            int i = 0;
            while (i < 100000) {
                sum += i;
                i++;
            }
            notify();
        }
    }
}
