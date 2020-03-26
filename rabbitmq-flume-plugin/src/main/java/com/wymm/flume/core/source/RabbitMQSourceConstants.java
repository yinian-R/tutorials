package com.wymm.flume.core.source;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Flume RabbitMQ Source 配置常量
 */
public class RabbitMQSourceConstants {
    
    /**
     * Default {@link ConnectionFactory#DEFAULT_HOST}
     */
    public static final String HOST = "rabbitmq.host";
    
    /**
     * Default {@link AMQP.PROTOCOL#PORT}
     */
    public static final String PORT = "rabbitmq.port";
    
    /**
     * Default {@link ConnectionFactory#DEFAULT_USER}
     */
    public static final String USERNAME = "rabbitmq.username";
    
    /**
     * Default {@link ConnectionFactory#DEFAULT_PASS}
     */
    public static final String PASSWORD = "rabbitmq.password";
    
    public static final String QUEUE = "rabbitmq.queue";
    
    public static final String PREFETCH_COUNT = "rabbitmq.prefetch-count";
    
    /**
     * Translate Date Class
     */
    public static final String CONVERTER = "converter";
    
    
    public static final String CONSUMER_THREAD_COUNT = "rabbitmq.consumer.thread.count";
    
}
