package com.wymm.springretrysample.service;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class AuthRetryInterceptor implements MethodInterceptor {
    
    @Autowired
    private RetryService retryService;
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("run AuthRetryInterceptor");
        return invocation.proceed();
    }
}
