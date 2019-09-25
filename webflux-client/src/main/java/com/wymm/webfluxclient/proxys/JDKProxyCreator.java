package com.wymm.webfluxclient.proxys;

import com.wymm.core.ApiServer;
import com.wymm.webfluxclient.beans.MethodInfo;
import com.wymm.webfluxclient.beans.ServerInfo;
import com.wymm.webfluxclient.interfaces.ProxyCreator;
import com.wymm.webfluxclient.interfaces.RestHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy:" + type);
        // 根据接口得到API的服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("serverInfo:" + serverInfo);
        // 给每一个代理类一个实现
        RestHandle handle = null;
        
        // 初始化服务器信息、webclient
        handle.init(serverInfo);
        
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 根据方法和参数得到调用的信息
                MethodInfo methodInfo = extractMethodInfo(method, args);
                log.info("methodInfo:" + methodInfo);
                // 调用 rest
                return handle.invokeRest(methodInfo);
            }
        });
    }
    
    /**
     * 根据方法定义和调用参数的调用的相关信息
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        // 得到请求URL和请求方法
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation :annotations){
            if (annotation instanceof GetMapping){
                GetMapping a = (GetMapping) annotation;
                
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            } else if(annotation instanceof PostMapping){
                PostMapping a = (PostMapping) annotation;
    
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            } else if(annotation instanceof DeleteMapping){
                DeleteMapping a = (DeleteMapping) annotation;
    
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            } else if(annotation instanceof PutMapping){
                PutMapping a = (PutMapping) annotation;
    
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }
            
        }
        
        // 得到调用的参数和body
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            // 是否带有@PathVariable
            
        }
        
        return methodInfo;
    }
    
    /**
     * 提取服务器信息
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }
}
