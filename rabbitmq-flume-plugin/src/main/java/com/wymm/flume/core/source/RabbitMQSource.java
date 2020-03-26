package com.wymm.flume.core.source;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.flume.Context;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.FlumeException;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.ConfigurationException;
import org.apache.flume.source.AbstractSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Flume RabbitMQ Source Class
 * 主要用于配置属性和创建消费线程
 */
@Slf4j
public class RabbitMQSource extends AbstractSource implements Configurable, EventDrivenSource {
    
    private Context context;
    private ConnectionFactory factory;
    private String queue;
    private int prefetchCount;
    private int consumerThreadCount;
    private Class<?> converterType;
    private List<Consumer> consumers = new ArrayList<>();
    
    public void configure(Context context) {
        this.context = context;
        
        String host = context.getString(RabbitMQSourceConstants.HOST, ConnectionFactory.DEFAULT_HOST);
        Integer port = context.getInteger(RabbitMQSourceConstants.PORT, AMQP.PROTOCOL.PORT);
        String username = context.getString(RabbitMQSourceConstants.USERNAME, ConnectionFactory.DEFAULT_USER);
        String password = context.getString(RabbitMQSourceConstants.PASSWORD, ConnectionFactory.DEFAULT_PASS);
        queue = context.getString(RabbitMQSourceConstants.QUEUE);
        if (queue == null) {
            throw new ConfigurationException("rabbitmq queue cannot be empty");
        }
        prefetchCount = context.getInteger(RabbitMQSourceConstants.PREFETCH_COUNT, 1);
        consumerThreadCount = context.getInteger(RabbitMQSourceConstants.CONSUMER_THREAD_COUNT, 1);
        if (consumerThreadCount < 1) {
            throw new IllegalArgumentException("rabbitmq consumer thread count cannot be less than 1");
        }
        
        String converter = context.getString(RabbitMQSourceConstants.CONVERTER);
        if (converter != null) {
            try {
                converterType = Class.forName(converter);
                this.testConverterInstance(converterType);
            } catch (Exception e) {
                throw new ConfigurationException("converter is error", e);
            }
        }
        
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        try {
            this.testRabbitMQConnection(factory);
        } catch (TimeoutException | IOException e) {
            throw new FlumeException("create RabbitMQ connection error", e);
        }
    }
    
    public synchronized void start() {
        for (int i = 0; i < consumerThreadCount; ++i) {
            Consumer consumer = new Consumer();
            consumer.setFactory(factory);
            consumer.setQueue(queue);
            consumer.setChannelProcessor(super.getChannelProcessor());
            consumer.setContext(context);
            consumer.setConverterType(converterType);
            consumer.setPrefetchCount(prefetchCount);
            Thread thread = new Thread(consumer);
            thread.setName("rabbitmq-consumer-thread-" + i);
            thread.start();
            consumers.add(consumer);
        }
        
        super.start();
    }
    
    public synchronized void stop() {
        consumers.forEach(Consumer::close);
        super.stop();
    }
    
    private void testConverterInstance(Class<?> converterType) throws IllegalAccessException, InstantiationException {
        converterType.newInstance();
    }
    
    private void testRabbitMQConnection(ConnectionFactory factory) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        connection.close();
    }
}
