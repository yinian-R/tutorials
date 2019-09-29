package com.wymm.webfluxclient.proxys;

import com.wymm.core.ApiServer;
import com.wymm.webfluxclient.beans.MethodInfo;
import com.wymm.webfluxclient.beans.ServerInfo;
import com.wymm.webfluxclient.interfaces.ProxyCreator;
import com.wymm.webfluxclient.interfaces.RestHandler;
import com.wymm.webfluxclient.resthandlers.WebClientRestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy:" + type);
        // 根据接口提取API的服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("serverInfo:" + serverInfo);
        // 给每一个代理类一个实现
        RestHandler handler = new WebClientRestHandler();
        
        // 初始化服务器信息、webclient
        handler.init(serverInfo);
    
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, (proxy, method, args) -> {
            // 根据方法和参数提取调用的信息
            MethodInfo methodInfo = extractMethodInfo(method, args);
            log.info("methodInfo:" + methodInfo);
            // 调用 rest
            return handler.invokeRest(methodInfo);
        });
    }
    
    /**
     * 根据方法定义和调用参数的调用的相关信息
     *
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
    
        // 提取请求URL和请求方法
        extractUrlAndMethod(method, methodInfo);
    
        // 提取调用的参数和body
        extractRequestAndBody(method, args, methodInfo);
    
        // 提取返回对象的信息
        extractReturnInfo(method, methodInfo);
    
        return methodInfo;
    }
    
    /**
     * 提取返回对象的信息
     *
     * @param method
     * @param methodInfo
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
        // 返回是flux还是mono
        // isAssignableFrom判断类型是否某个的子类
        // instanceof 判断实例是否某个的子类
        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(isFlux);
        
        // 提取返回对象的实际类型
        Class<?> elementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(elementType);
    }
    
    /**
     * 提取返泛型类型的实际类型
     *
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }
    
    /**
     * 提取调用的参数和body
     *
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractRequestAndBody(Method method, Object[] args, MethodInfo methodInfo) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            // 参数和值对应的map
            Map<String, Object> params = new LinkedHashMap<>();
            methodInfo.setParams(params);
            
            // 是否带有@PathVariable
            PathVariable annotationPath = parameters[i].getAnnotation(PathVariable.class);
            if (annotationPath != null) {
                params.put(annotationPath.value(), args[i]);
            }
            
            // 是否带有RequestBody
            RequestBody annotationBody = parameters[i].getAnnotation(RequestBody.class);
            if (annotationBody != null) {
                methodInfo.setBody(Mono.just(args[i]));
            }
        }
    }
    
    /**
     * 提取请求URL和请求方法
     *
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetMapping) {
                GetMapping a = (GetMapping) annotation;
                
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            } else if (annotation instanceof PostMapping) {
                PostMapping a = (PostMapping) annotation;
                
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            } else if (annotation instanceof DeleteMapping) {
                DeleteMapping a = (DeleteMapping) annotation;
                
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            } else if (annotation instanceof PutMapping) {
                PutMapping a = (PutMapping) annotation;
                
                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }
            
        }
    }
    
    /**
     * 提取服务器信息
     *
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
