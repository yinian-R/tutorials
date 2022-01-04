package com.wymm.concurrent.base.semaphore;

import java.util.concurrent.Semaphore;

public class LoginQueueUsingSemaphore {
    
    private Semaphore semaphore;
    
    public LoginQueueUsingSemaphore(int slotLimit) {
        this.semaphore = new Semaphore(slotLimit);
    }
    
    /**
     * 如果立即可获得许可证，则返回true，否则获取false。acquire() 获取许可证并阻塞直到一个可用
     */
    public boolean tryLogin() {
        return semaphore.tryAcquire();
    }
    
    /**
     * 释放许可证
     */
    public void logout() {
        semaphore.release();
    }
    
    /**
     * 返回当前可用的许可证数量
     */
    public int availableSlots() {
        return semaphore.availablePermits();
    }
}
