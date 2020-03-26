package com.wymm.flume.core.rabbitmq;

import org.junit.jupiter.api.Test;

class RabbitConfigTest {
    
    @Test
    void testRabbitMQConnection() throws Exception {
        RabbitConfig.testRabbitMQConnection();
    }
    
    @Test
    void send() throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "{\"gcbh\":\"0b2ae314f455479eb9b571c73d935840\",\"sbxh\":\"C71C485587E00001D2121C80EFB0252026\",\"jgsjStr\":\"2020-02-17 15:14:10.156\",\"hphm\":\"桂DRP783\",\"hpzl\":\"02\",\"hpys\":\"2\",\"cwkc\":0,\"clys\":\"A\",\"cllx\":\"K31\",\"sd\":87,\"cdbh\":2,\"fx\":\"2\",\"wfdm\":\"\",\"tpurl\":\"http://44.211.75.22:50530/p/44_211_75_22_50530/G/MTU4MTkyMzY2MDYzN185NTJhN2FlMl8yNTkyMDAwMDBfUEFTUw\\\\u003d\\\\u003d.jpg\",\"tpurl1\":\"\",\"tpurl2\":\"\",\"lsh\":\"0b2ae314f455479eb9b571c73d935840\",\"jgsj\":\"2020-02-17 15:14:10.156\",\"hjsj\":\"2020-02-17 15:14:11.263\",\"scsj\":\"2020-02-17 15:14:11.263\",\"ffsj\":\"2020-02-17 15:14:11.263\",\"jrsj\":\"2020-02-17 15:14:19.619\"}";
            RabbitConfig.send(message);
        }
    }
    
    @Test
    void consumer() throws Exception {
        RabbitConfig.consumer();
        Thread.sleep(3000);
    }
    
    @Test
    void createQueue() throws Exception {
        RabbitConfig.createQueue();
    }
    
    
}