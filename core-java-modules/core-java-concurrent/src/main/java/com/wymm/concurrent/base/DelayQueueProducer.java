package com.wymm.concurrent.base;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class DelayQueueProducer implements Runnable {
    
    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToProducer;
    private Integer delayOfEachProducedMessageMilliseconds;
    
    public DelayQueueProducer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProducer, Integer delayOfEachProducedMessageMilliseconds) {
        this.queue = queue;
        this.numberOfElementsToProducer = numberOfElementsToProducer;
        this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < numberOfElementsToProducer; i++) {
                DelayObject delayObject = new DelayObject(UUID.randomUUID().toString(), delayOfEachProducedMessageMilliseconds);
                log.info("producer put:" + delayObject);
                queue.put(delayObject);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
