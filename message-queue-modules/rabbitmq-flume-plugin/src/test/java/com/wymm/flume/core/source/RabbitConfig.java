package com.wymm.flume.core.source;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitConfig {
    
    public static final String QUEUE_NAME = "rabbit-java-sample";
    public static final String TOPIC_EXCHANGE_NAME = "rabbit-java-sample-exchange";
    
    
    public static void main(String[] args) throws Exception {
        consumer();
    }
    
    public static void createQueue(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, TOPIC_EXCHANGE_NAME, "foo.bar.#");
    }
    
    
    /**
     * 消费者接收消息数据
     */
    public static void consumer() throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        
        // 没创建的时候才创建
        createQueue(channel);
        
        channel.basicQos(1);
        
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
    }
    
    /**
     * 生产者发送消息数据
     */
    public static void send(String message) throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        
        //String message = "hello world";
        channel.basicPublish(TOPIC_EXCHANGE_NAME, "foo.bar.baz", null, message.getBytes());
        
        System.out.println("生产者发送：" + message);
        
        channel.close();
        connection.close();
    }
    
    /**
     * 获取rabbit连接
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }
    
    /**
     * 测试是否能连接通
     */
    public static void testRabbitMQConnection() throws Exception {
        Connection connection = getConnection();
        connection.close();
    }
}
