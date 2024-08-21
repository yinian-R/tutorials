package com.example.springwebredis.message;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedissonDelayQueue {

    @Autowired
    private RedissonClient redissonClient;

    private RDelayedQueue<String> delayQueue;
    private RBlockingQueue<String> blockingQueue;

    @PostConstruct
    public void init() {
        initDelayQueue();
        startDelayQueueConsumer();
    }

    private void initDelayQueue() {
        blockingQueue = redissonClient.getBlockingQueue("SANYOU");
        delayQueue = redissonClient.getDelayedQueue(blockingQueue);
    }

    @Scheduled(fixedDelay = 1000)
    private void startDelayQueueConsumer() {
        try {
            log.info("监听延迟任务");
            String task = blockingQueue.take();
            log.info("接收到延迟任务:{}", task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offerTask(String task, long seconds) {
        log.info("添加延迟任务:{} 延迟时间:{}s", task, seconds);
        delayQueue.offer(task, seconds, TimeUnit.SECONDS);
    }

}
