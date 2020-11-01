package com.wymm.core.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 请求接口方法调用信息类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodInfo {
    
    // 请求 URL
    private String url;
    
    // 请求方法
    private HttpMethod method;
    
    // 请求参数
    private Map<String, Object> params;
    
    // 请求 body
    private Mono body;
    
    // 请求 body 的类型
    private Class<?> bodyElementType;
    
    /**
     * 返回是flux还是mono
     */
    private boolean returnFlux;
    
    /**
     * 返回对象的类型
     */
    private Class<?> returnElementType;
}
