package com.example.messagingstompwebsocket.socket.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Spring MyBean在第一次从控制器访问它时初始化一个新实例，并将该实例存储在 WebSocket 会话属性中。随后返回相同的实例，直到会话结束。
 */
@Slf4j
@Component
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyBean {
    
    @Getter
    @Setter
    private String uid;
    
    @PostConstruct
    public void init() {
        // Invoked after dependencies injected
        log.info("init");
    }
    
    // ...
    
    @PreDestroy
    public void destroy() {
        // Invoked when the WebSocket session ends
        log.info("destroy");
    }
    
}
