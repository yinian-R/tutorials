package com.wymm.flume.core.source;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;

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
    public static final String TRANSLATE_DATA_HANDLER = "translateDataHandler";
    
}
