package com.example.messagingstompwebsocket.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 使用@EnableWebSocketMessageBroker注释此类，顾名思义，它支持 WebSocket 消息处理，由消息代理提供支持
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 我们通过指定“/app”前缀来完成我们的简单配置，目的是以过滤针对应用程序注释方法（通过@MessageMapping）。
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic");
    }
    
    /**
     * StompEndpointRegistry 方法注册“gs-guide-websocket”端点，使Spring 支持 STOMP
     * 这个端点，当前缀为“app”时，映射到 GreetingController.greeting() 处理端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
    
}
