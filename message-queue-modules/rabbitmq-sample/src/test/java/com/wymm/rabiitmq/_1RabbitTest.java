package com.wymm.rabiitmq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

class _1RabbitTest {
    
    private static final String QUEUE_NAME = "rabbit-java-sample";
    private static final String TOPIC_EXCHANGE_NAME = "rabbit-java-sample-exchange";
    
    private Connection connection;
    
    @BeforeEach
    void setup() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("root");
        factory.setPassword("root");
        connection = factory.newConnection();
        
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, TOPIC_EXCHANGE_NAME, "foo.bar.#");
        channel.close();
    }
    
    @AfterEach
    void destroy() {
        try {
            connection.close();
        } catch (Exception ignore) {
        }
    }
    
    /**
     * 发送数据
     */
    @Test
    void send() throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        for (int i = 0; i < 1000; i++) {
            String message = "hello world:" + i;
            channel.basicPublish(TOPIC_EXCHANGE_NAME, "foo.bar.baz", null, message.getBytes());
            System.out.println("生产者发送：" + message);
        }
        
        try {
            channel.close();
        } catch (Exception ignore) {
        }
    }
    
    /**
     * 消费者接收消息数据
     */
    @Test
    void consume() throws IOException, TimeoutException, InterruptedException {
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Consumer consumer = new DefaultConsumer(channel) {
            
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("消费者接收数据：" + message);
                
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
            
        };
        channel.basicConsume(QUEUE_NAME, consumer);
        
        
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
            @Override
            public void run() {
                try {
                    channel.close();
                } catch (Exception ignore) {
                }
            }
        });
        countDownLatch.await();
    }
}
