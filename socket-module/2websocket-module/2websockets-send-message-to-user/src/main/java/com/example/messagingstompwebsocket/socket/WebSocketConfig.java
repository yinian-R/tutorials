package com.example.messagingstompwebsocket.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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
        config.enableSimpleBroker("/topic/", "/queue/");
        // 点对点时设置前缀，默认是user
        config.setUserDestinationPrefix("/user");
    }
    
    /**
     * StompEndpointRegistry 方法注册“gs-guide-websocket”端点，使Spring 支持 STOMP
     * 这个端点，当前缀为“app”时，映射到 GreetingController.greeting() 处理端点
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/guide-websocket")
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                                      Map<String, Object> attributes) {
                        // 可根据系统的认证方式修改，为实现发送信息给指定用户做准备
                        URI uri = request.getURI();
                        UriComponents components = UriComponentsBuilder.fromUri(uri).build();
                        List<String> uidList = components.getQueryParams().get("uid");
                        if (!ObjectUtils.isEmpty(uidList)) {
                            String uid = uidList.get(0);
                            return () -> uid;
                        } else {
                            return super.determineUser(request, wsHandler, attributes);
                        }
                    }
                })
                .withSockJS();
    }
    
}
