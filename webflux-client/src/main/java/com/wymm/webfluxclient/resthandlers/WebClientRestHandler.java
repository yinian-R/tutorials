package com.wymm.webfluxclient.resthandlers;

import com.wymm.webfluxclient.beans.MethodInfo;
import com.wymm.webfluxclient.beans.ServerInfo;
import com.wymm.webfluxclient.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

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
        WebClient.ResponseSpec request = this.client
                .method(methodInfo.getMethod())
                //.uri(methodInfo.getUrl())
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                .accept(MediaType.APPLICATION_JSON)
                // 发送请求
                .retrieve();
        if (methodInfo.isReturnFlux()) {
            result = request.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = request.bodyToMono(methodInfo.getReturnElementType());
        }
        return result;
    }
}
