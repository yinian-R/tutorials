package com.wymm.flume.core.rabbitmq.source;

import com.rabbitmq.client.*;
import com.wymm.flume.core.rabbitmq.Converter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.event.SimpleEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 消费线程类
 * 消费数据，转换后推送给 channel
 */
@Slf4j
public class Consumer implements Runnable {
    
    private static final String FLUME_CONSUMER_NAME = "flumeConsumer";
    @Setter
    private ConnectionFactory factory;
    @Setter
    private String queue;
    @Setter
    private int prefetchCount;
    
    @Setter
    private Class<?> converterType;
    private Converter converter;
    @Setter
    private Boolean converterConsumerSingleton;
    
    private Connection connection;
    private Channel channel;
    
    @Setter
    private Context context;
    @Setter
    private ChannelProcessor channelProcessor;
    
    @Override
    public void run() {
        try {
            try {
                if (converterConsumerSingleton && converterType != null) {
                    converter = (Converter) converterType.newInstance();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("Error Converter Instance", e);
                return;
            }
            
            try {
                connection = factory.newConnection();
                channel = connection.createChannel();
            } catch (IOException | TimeoutException e) {
                log.error("Error RabbitMQ connection", e);
                return;
            }
            
            try {
                channel.basicQos(prefetchCount);
            } catch (IOException e) {
                log.error("Error setting prefetchCount", e);
                return;
            }
            
            
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        String message = new String(body, StandardCharsets.UTF_8);
                        Event event = new SimpleEvent();
                        event.setBody(message.getBytes());
                        
                        if (!converterConsumerSingleton && converterType != null) {
                            try {
                                converter = (Converter) converterType.newInstance();
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new FlumeException("Error Instance Converter", e);
                            }
                        }
                        if (converter != null) {
                            event = converter.convert(event, context);
                        }
                        
                        channelProcessor.processEvent(event);
                        
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Throwable e) {
                        log.error(e.getMessage(), e);
                        throw e;
                    }
                }
                
            };
            
            try {
                channel.basicConsume(queue, false, FLUME_CONSUMER_NAME, consumer);
            } catch (IOException e) {
                log.error("Error start RabbitMQ consumer", e);
                this.close();
            }
            
        } catch (Throwable e) {
            log.error("Error Unknown Throwable", e);
            this.shutdown();
        }
    }
    
    /**
     * 关闭线程
     */
    public void shutdown() {
        try {
            channel.basicCancel(FLUME_CONSUMER_NAME);
        } catch (IOException e) {
            log.error("Error RabbitMQ basic cancel", e);
        }
        
        this.close();
    }
    
    /**
     * 关闭连接
     */
    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            log.error("Error close RabbitMQ connection", e);
        }
    }
}
