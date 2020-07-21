package com.wymm.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BlockingQueue 队列。常用于解决并发的生产者-消费者的问题。
 * <p>
 * BlockingQueue 中构建队列最重要的方法：
 * 添加元素
 * add() – 如果插入成功，则返回true，否则抛出IllegalStateException
 * put() – 将指定的元素插入队列，并在必要时等待可用插槽
 * offer() – 如果插入成功，则返回true，否则返回false
 * offer(E e, long timeout, TimeUnit unit) – 尝试将元素插入队列，并等待指定超时内的可用插槽
 * 检索元素
 * take() – 等待队列的head元素并将其删除。如果队列为空，它将阻塞并等待元素变为可用
 * poll(long timeout, TimeUnit unit) – 检索并删除队列的开头，如果有必要，等待最长指定的等待时间以使元素可用。超时后返回null
 */
class _8BlockingQueueTest {
    
    /**
     * 创建无界队列 - 可以无限制增长
     */
    @Test
    void createUnboundedQueue() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    }
    
    /**
     * 创建有界队列 - 定义了最大容量
     */
    @Test
    void createBoundedQueue() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
    }
    
    /**
     * 构建多线程的生产者-消费者模式
     * <p>
     * 创建一个容量为10的有界队列
     * 生产者产生一个从0到100的随机数，并将数据放到队列 BlockingQueue 中。我们将有4个生产者使用 put() 方法进行阻塞，知道队列中有可用空间为止。
     * <p>
     * 更要记得的一点，我们要阻止消费者线程无限期地等待元素出现在队列中。
     * 从生产者中发出不再需要处理的消息是一种好方法，是发送一种叫做“poisonPill”的特殊消息。然后当消费者从队列中取出“poisonPill”，线程正常完成执行。
     */
    @Test
    void producerAndConsumerPattern() throws InterruptedException {
        int CAPACITY = 10;
        int N_PRODUCERS = 5;
        // 获取可用处理器数量
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
        int mod = N_CONSUMERS % N_PRODUCERS;
        
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(CAPACITY);
        
        for (int i = 0; i < N_PRODUCERS; i++) {
            if (i == N_PRODUCERS - 1) {
                new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer + mod)).start();
            } else {
                new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
            }
        }
        
        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new NumbersConsumer(queue, poisonPill)).start();
        }
        
        Thread.sleep(1000);
        
        assertEquals(0, queue.size());
    }
    
}
