package com.wymm.flume.core.source;

import com.rabbitmq.client.*;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.FlumeException;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.ConfigurationException;
import org.apache.flume.event.JSONEvent;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;
import org.apache.flume.source.kafka.KafkaSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQSource extends AbstractSource implements Configurable, EventDrivenSource {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQSource.class);
    
    Connection connection;
    
    public void configure(Context context) {
        
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~configure start");
        
        String host = context.getString(RabbitMQSourceConstants.HOST, ConnectionFactory.DEFAULT_HOST);
        Integer port = context.getInteger(RabbitMQSourceConstants.PORT, AMQP.PROTOCOL.PORT);
        String username = context.getString(RabbitMQSourceConstants.USERNAME, ConnectionFactory.DEFAULT_USER);
        String password = context.getString(RabbitMQSourceConstants.PASSWORD, ConnectionFactory.DEFAULT_PASS);
        String queue = context.getString(RabbitMQSourceConstants.QUEUE);
        if (queue == null) {
            throw new ConfigurationException("rabbitmq queue cannot be empty");
        }
        
        // config factory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            // todo
            throw new FlumeException(e);
        }
    
        try {
            consumer(queue);
        } catch (Exception e) {
            throw new FlumeException(e);
        }
    
        print("~~~~~~~~~~~~~~~~~~~~~~~~~configure start final");
    
    }
    
    @Override
    public synchronized void start() {
        print("start start");
        super.start();
    }
    
    public void consumer(String queue) throws Exception {
        Channel channel = connection.createChannel();
    
        // 没创建的时候才创建
        //createQueue(channel);
    
        channel.basicQos(1);
    
        ChannelProcessor channelProcessor = super.getChannelProcessor();
    
        Consumer consumer = new DefaultConsumer(channel) {
        
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, StandardCharsets.UTF_8);
                print("消费者接收数据：" + message);
    
                SimpleEvent event = new SimpleEvent();
                event.setBody(message.getBytes());
                //channelProcessor.processEvent(event);
            
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        
        };
    
        channel.basicConsume(queue, consumer);
    }
    
    
    private void print(String msg){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:"+msg);
    }
}
