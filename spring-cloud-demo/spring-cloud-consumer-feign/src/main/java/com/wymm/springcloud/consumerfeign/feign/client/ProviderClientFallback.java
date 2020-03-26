package com.wymm.springcloud.consumerfeign.feign.client;

import org.springframework.stereotype.Component;

@Component
public class ProviderClientFallback implements ProviderClient {
    
    @Override
    public String sayHi(String name) {
        return "hi " + name + "; sorry error";
    }
}
