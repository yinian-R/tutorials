package com.wymm.flume.core.source;

import com.rabbitmq.client.*;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.FlumeException;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.ConfigurationException;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQSource extends AbstractSource implements Configurable, EventDrivenSource {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQSource.class);
    
    private static final String FLUME_CONSUMER_NAME = "flumeConsumer";
    
    private Context context;
    
    
    private Connection connection;
    private Channel channel;
    
    private String queue;
    private int prefetchCount = 1;
    private boolean autoAck = false;
    
    private RabbitDemoTran rabbitDemoTran;
    
    
    private ChannelProcessor channelProcessor;
    
    public void configure(Context context) {
    
        this.context = context;
        
        String host = context.getString(RabbitMQSourceConstants.HOST, ConnectionFactory.DEFAULT_HOST);
        Integer port = context.getInteger(RabbitMQSourceConstants.PORT, AMQP.PROTOCOL.PORT);
        String username = context.getString(RabbitMQSourceConstants.USERNAME, ConnectionFactory.DEFAULT_USER);
        String password = context.getString(RabbitMQSourceConstants.PASSWORD, ConnectionFactory.DEFAULT_PASS);
        queue = context.getString(RabbitMQSourceConstants.QUEUE);
        prefetchCount = context.getInteger(RabbitMQSourceConstants.PREFETCH_COUNT, 1);
        if (queue == null) {
            throw new ConfigurationException("rabbitmq queue cannot be empty");
        }
        String translateDataHandlerClass = context.getString(RabbitMQSourceConstants.TRANSLATE_DATA_HANDLER);
        
        if(translateDataHandlerClass != null){
            try {
                rabbitDemoTran = (RabbitDemoTran) ClassUtils
                        .forName(translateDataHandlerClass, ClassUtils.getDefaultClassLoader())
                        .newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            throw new FlumeException("Error create RabbitMQ connection", e);
        }
        
    }
    
    @Override
    public synchronized void start() {
        
        channelProcessor = super.getChannelProcessor();
        try {
            consumer(queue);
        } catch (Exception e) {
            throw new FlumeException(e);
        }
        
        super.start();
    }
    
    @Override
    public synchronized void stop() {
        try {
            channel.basicCancel(FLUME_CONSUMER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        super.stop();
    }
    
    private void consumer(String queue) throws Exception {
        channel = connection.createChannel();
        
        channel.basicQos(prefetchCount);
        
        Consumer consumer = new DefaultConsumer(channel) {
            
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                Event event = new SimpleEvent();
                event.setBody(message.getBytes());
    
                if(rabbitDemoTran != null){
                    event = rabbitDemoTran.doTranslateData(event, context);
                }
                
                channelProcessor.processEvent(event);
                
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
            
        };
        
        channel.basicConsume(queue, autoAck, FLUME_CONSUMER_NAME, consumer);
    }
    
    private void print(String msg) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:" + msg);
    }
}
