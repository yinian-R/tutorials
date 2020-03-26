package com.wymm.flume.core.rabbitmq.source;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wymm.flume.core.rabbitmq.Converter;
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
import java.util.Objects;
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
    private boolean autoAck;
    private int prefetchCount;
    private int consumerThreadCount;
    private Boolean converterConsumerSingleton;
    private Class<?> converterType;
    private List<Consumer> consumers = new ArrayList<>();
    
    public void configure(Context context) {
        try {
            this.context = context;
            
            String host = context.getString(RabbitMQSourceConstants.HOST, ConnectionFactory.DEFAULT_HOST);
            Integer port = context.getInteger(RabbitMQSourceConstants.PORT, AMQP.PROTOCOL.PORT);
            String virtualHost = context.getString(RabbitMQSourceConstants.VIRTUAL_HOST, ConnectionFactory.DEFAULT_VHOST);
            String username = context.getString(RabbitMQSourceConstants.USERNAME, ConnectionFactory.DEFAULT_USER);
            String password = context.getString(RabbitMQSourceConstants.PASSWORD, ConnectionFactory.DEFAULT_PASS);
            queue = context.getString(RabbitMQSourceConstants.QUEUE);
            autoAck = context.getBoolean(RabbitMQSourceConstants.AUTO_ACK, false);
            if (queue == null) {
                throw new ConfigurationException(String.format("%s cannot be empty", RabbitMQSourceConstants.QUEUE));
            }
            prefetchCount = context.getInteger(RabbitMQSourceConstants.PREFETCH_COUNT, 1);
            consumerThreadCount = context.getInteger(RabbitMQSourceConstants.CONSUMER_THREAD_COUNT, 1);
            if (consumerThreadCount < 1) {
                throw new IllegalArgumentException(String.format("%s cannot be less than 1", RabbitMQSourceConstants.CONSUMER_THREAD_COUNT));
            }
            
            // 配置转换类
            String converter = context.getString(RabbitMQSourceConstants.CONVERTER);
            if (converter != null) {
                try {
                    converterType = Class.forName(converter);
                    this.testConverterInstance(converterType);
                } catch (Exception e) {
                    throw new ConfigurationException(String.format("%s is error", RabbitMQSourceConstants.CONVERTER), e);
                }
            }
            converterConsumerSingleton = context.getBoolean(RabbitMQSourceConstants.CONVERTER_CONSUMER_SINGLETON, false);
            
            // 配置RabbitMQ连接工厂
            factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setVirtualHost(virtualHost);
            factory.setUsername(username);
            factory.setPassword(password);
            try {
                this.testRabbitMQConnection(factory);
            } catch (TimeoutException | IOException e) {
                throw new FlumeException("create RabbitMQ connection error", e);
            }
    
    
            // 创建队列
            Boolean create = context.getBoolean(RabbitMQSourceConstants.CREATE_QUEUE, false);
            if (create) {
                String topicExchange = context.getString(RabbitMQSourceConstants.TOPIC_EXCHANGE);
                String routingKey = context.getString(RabbitMQSourceConstants.ROUTING_KEY);
                this.createQueue(queue, topicExchange, routingKey);
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    
    public synchronized void start() {
        for (int i = 0; i < consumerThreadCount; ++i) {
            // 创建消费线程
            Consumer consumer = new Consumer();
            consumer.setFactory(factory);
            consumer.setQueue(queue);
            consumer.setChannelProcessor(super.getChannelProcessor());
            consumer.setContext(context);
            consumer.setConverterType(converterType);
            consumer.setPrefetchCount(prefetchCount);
            consumer.setConverterConsumerSingleton(converterConsumerSingleton);
            Thread thread = new Thread(consumer);
            thread.setName("rabbitmq-consumer-thread-" + i);
            thread.start();
            consumers.add(consumer);
        }
        
        super.start();
    }
    
    public synchronized void stop() {
        consumers.forEach(Consumer::shutdown);
        super.stop();
    }
    
    /**
     * 创建Queue
     *
     * @param queue      队列
     * @param exchange   交换机
     * @param routingKey 路由
     */
    protected void createQueue(String queue, String exchange, String routingKey) {
        Objects.requireNonNull(queue);
        Objects.requireNonNull(exchange);
        Objects.requireNonNull(routingKey);
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, routingKey);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException("create queue error", e);
        }
    }
    
    /**
     * 验证转换类实例
     *
     * @param converterType 转换类
     */
    private void testConverterInstance(Class<?> converterType) throws IllegalAccessException, InstantiationException {
        Converter o = (Converter) converterType.newInstance();
    }
    
    /**
     * 验证RabbitMQ连接
     *
     * @param factory ConnectionFactory
     */
    private void testRabbitMQConnection(ConnectionFactory factory) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        connection.close();
    }
}
