package com.example.springwebredis.core.message;

import org.springframework.lang.NonNull;

/**
 * Redis 消费者接口
 * @author hrj
 * @since 2023-07-04
 */
public interface RedisConsumer {
    
    /**
     * @return PatternTopic String
     */
    @NonNull
    String getPatternTopic();
    
    /**
     * 判断是否消费消息，若为 true 进入 handleMessage 方法
     *
     * @param message message
     * @param pattern pattern
     * @return true 消费消息 false 不消费消息
     */
    default boolean support(String message, String pattern) {
        return getPatternTopic().equals(pattern);
    }
    
    /**
     * 消费消息
     *
     * @param message message
     * @param pattern pattern
     */
    default void handleMessage(String message, String pattern) {
        try {
            doHandleMessage(message, pattern);
        } catch (Exception e) {
            errorHandle(e, message, pattern);
        }
    }
    
    /**
     * 消费消息
     *
     * @param message message
     * @param pattern pattern
     */
    void doHandleMessage(String message, String pattern);
    
    /**
     * 消费错误异常处理
     *
     * @param e       消费错误异常
     * @param message message
     * @param pattern pattern
     */
    void errorHandle(Exception e, String message, String pattern);
    
}
