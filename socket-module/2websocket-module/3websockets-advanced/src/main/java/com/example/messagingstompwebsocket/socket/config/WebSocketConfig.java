package com.example.messagingstompwebsocket.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 使用@EnableWebSocketMessageBroker注释此类，顾名思义，它支持 WebSocket 消息处理，由消息代理提供支持
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 我们通过指定“/app”前缀来完成我们的简单配置，目的是以过滤针对应用程序注释方法（通过@MessageMapping）。
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic/", "/queue/");
        // 点对点时设置前缀，默认是user
        registry.setUserDestinationPrefix("/user");
        // 客户端接收消息顺序，需要时启用此功能，因为保持消息的顺序需要一些性能开销
        registry.setPreservePublishOrder(true);
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
                /**
                 * 不使用 URL 的认证方式，避免打印日志时带着用户用户信息
                 */
                //.setHandshakeHandler(new DefaultHandshakeHandler() {
                //    @Override
                //    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                //                                      Map<String, Object> attributes) {
                //        // 可根据系统的认证方式修改，为实现发送信息给指定用户做准备
                //        URI uri = request.getURI();
                //        System.out.println(uri);
                //        UriComponents components = UriComponentsBuilder.fromUri(uri).build();
                //        List<String> uidList = components.getQueryParams().get("uid");
                //        if (!ObjectUtils.isEmpty(uidList)) {
                //            String uid = uidList.get(0);
                //            return () -> uid;
                //        } else {
                //            return super.determineUser(request, wsHandler, attributes);
                //        }
                //    }
                //})
                .withSockJS();
    }
    
    /**
     * 可使用 token 认证
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // access authentication header(s)
                    Map<String, Object> nativeHeaders = (Map<String, Object>) accessor.getHeader("nativeHeaders");
                    List<Object> tokenList = (List<Object>) nativeHeaders.get("token");
                    if (!ObjectUtils.isEmpty(tokenList)) {
                        Object token = tokenList.get(0);
                        Authentication user = new Authentication(ObjectUtils.getDisplayString(token));
                        accessor.setUser(user);
                    }
                }
                return message;
            }
        });
    }
    
}
