package com.wymm.concurrent.base;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    
    
    public CustomThreadFactory(String name) {
        this.namePrefix = name + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
    }
    
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, namePrefix + threadNumber.getAndIncrement());
    }
}