package com.wymm.flume.core.rabbitmq.source;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Flume RabbitMQ Source 配置常量
 */
public class RabbitMQSourceConstants {
    
    /**
     * RabbitMQ 主机
     * Default {@link ConnectionFactory#DEFAULT_HOST}
     */
    public static final String HOST = "rabbitmq.host";
    
    /**
     * RabbitMQ 端口
     * Default {@link AMQP.PROTOCOL#PORT}
     */
    public static final String PORT = "rabbitmq.port";
    
    /**
     * Default {@link ConnectionFactory#DEFAULT_VHOST}
     */
    public static final String VIRTUAL_HOST = "rabbitmq.virtualHost";
    
    /**
     * RabbitMQ 用户
     * Default {@link ConnectionFactory#DEFAULT_USER}
     */
    public static final String USERNAME = "rabbitmq.username";
    
    /**
     * RabbitMQ 密码
     * Default {@link ConnectionFactory#DEFAULT_PASS}
     */
    public static final String PASSWORD = "rabbitmq.password";
    
    /**
     * RabbitMQ 队列
     */
    public static final String QUEUE = "rabbitmq.queue";
    
    /**
     * 是否根据TOPIC_EXCHANGE、ROUTING_KEY创建QUEUE
     */
    public static final String CREATE_QUEUE = "rabbitmq.queue.create";
    
    /**
     * RabbitMQ 交换机
     */
    public static final String TOPIC_EXCHANGE = "rabbitmq.exchange";
    
    /**
     * RabbitMQ 路由
     */
    public static final String ROUTING_KEY = "rabbitmq.routingKey";
    
    /**
     * 预期接收数量
     */
    public static final String PREFETCH_COUNT = "rabbitmq.prefetch-count";
    
    /**
     * 是否开启自动确认ACK
     */
    public static final String AUTO_ACK = "rabbitmq.autoAck";
    
    /**
     * Converter Class
     */
    public static final String CONVERTER = "converter";
    
    /**
     * 每个Consumer仅有一个Converter实例
     */
    public static final String CONVERTER_CONSUMER_SINGLETON = "converter.consumer.singleton";
    
    /**
     * 消费线程数量
     */
    public static final String CONSUMER_THREAD_COUNT = "rabbitmq.consumer.thread.count";
    
}
