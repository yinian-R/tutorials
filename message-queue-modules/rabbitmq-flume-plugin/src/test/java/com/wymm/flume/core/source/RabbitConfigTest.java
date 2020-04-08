package com.wymm.flume.core.source;

import org.junit.jupiter.api.Test;

class RabbitConfigTest {
    
    @Test
    void testRabbitMQConnection() throws Exception {
        RabbitConfig.testRabbitMQConnection();
    }
    
    @Test
    void send() throws Exception {
        for (int i = 0; i < 20; i++) {
            String message = "hello world" + i;
            RabbitConfig.send(message);
        }
    }
    
    @Test
    void consumer() throws Exception {
        RabbitConfig.consumer();
    }
    
    
}