package com.wymm.core.resthandlers;

import com.wymm.core.beans.MethodInfo;
import com.wymm.core.beans.ServerInfo;
import com.wymm.core.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 请求调用处理类
 */
public class WebClientRestHandler implements RestHandler {
    
    private WebClient client;
    
    /**
     * 初始化webclient
     *
     * @param serverInfo 服务器信息
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getUrl());
    }
    
    /**
     * 处理rest请求
     *
     * @param methodInfo 接口信息
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        Object result;
        WebClient.RequestBodySpec request = this.client
                .method(methodInfo.getMethod())
                //.uri(methodInfo.getUrl())
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                .accept(MediaType.APPLICATION_JSON);
        
        WebClient.ResponseSpec retrieve;
        if (methodInfo.getBody() != null) {
            // 发送请求
            retrieve = request.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        } else {
            retrieve = request.retrieve();
        }
        
        // 处理异常
        retrieve.onStatus(
                httpStatus -> httpStatus.value() == 404,
                clientResponse -> Mono.just(new RuntimeException("Not Found"))
        );
        
        if (methodInfo.isReturnFlux()) {
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }
        return result;
    }
}
