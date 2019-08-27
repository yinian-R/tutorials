package com.wymm.webfluxclient.proxys;

import com.wymm.webfluxclient.beans.MethodInfo;
import com.wymm.webfluxclient.beans.ServerInfo;
import com.wymm.webfluxclient.interfaces.ProxyCreator;
import com.wymm.webfluxclient.interfaces.RestHandle;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
    
    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        return null;
    }
    
    
    private ServerInfo extractServerInfo(Class<?> type) {
        return null;
    }
}
