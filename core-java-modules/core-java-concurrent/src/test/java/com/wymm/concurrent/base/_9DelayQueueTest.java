package com.wymm.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DelayQueue 是无限大小的阻塞队列，只有元素的到期时间（称为用户定义的延迟）完成时才能拉取元素
 * <p>
 * getDelay() 返回负数的时候，意味着给定的程序已经过期。在这种情况下，生产者应该立即消费该元素。
 */
class _9DelayQueueTest {
    
    @Test
    void giveDelayQueue_whenProduceElement_thenShouldConsumerAfterGiveDelay() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        // give
        DelayQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProducer = 2;
        int delayOfEachProducedMessageMilliseconds = 500;
        
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProducer, delayOfEachProducedMessageMilliseconds);
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProducer);
        
        // when
        executorService.submit(producer);
        executorService.submit(consumer);
        
        // then
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        executorService.shutdown();
        
        assertEquals(numberOfElementsToProducer, consumer.numberOfConsumerElements.get());
    }
}
