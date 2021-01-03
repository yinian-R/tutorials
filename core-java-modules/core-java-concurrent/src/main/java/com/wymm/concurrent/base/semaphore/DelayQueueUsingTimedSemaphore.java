package com.wymm.concurrent.base.semaphore;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

import java.util.concurrent.TimeUnit;

public class DelayQueueUsingTimedSemaphore {
    
    private TimedSemaphore semaphore;
    
    public DelayQueueUsingTimedSemaphore(long timePeriod, int limit) {
        this.semaphore = new TimedSemaphore(timePeriod, TimeUnit.SECONDS, limit);
    }
    
    public boolean tryAdd() {
        return semaphore.tryAcquire();
    }
    
    public int availableSlots() {
        return semaphore.getAvailablePermits();
    }
}
