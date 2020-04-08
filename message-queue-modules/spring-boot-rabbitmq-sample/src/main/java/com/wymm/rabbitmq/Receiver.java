package com.wymm.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * 创建RabbitMQ消息接收器
 */
@Slf4j
@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);
    
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        latch.countDown();
    }
    
    public CountDownLatch getLatch() {
        return latch;
    }
}
