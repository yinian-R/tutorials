package com.wymm.concurrent.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DelayQueueConsumer implements Runnable {
    
    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToTake;
    
    public AtomicInteger numberOfConsumerElements = new AtomicInteger();
    
    public DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToTake) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfElementsToTake;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < numberOfElementsToTake; i++) {
                DelayObject take = queue.take();
    
                numberOfConsumerElements.getAndIncrement();
                log.info("consumer get:" + take);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
