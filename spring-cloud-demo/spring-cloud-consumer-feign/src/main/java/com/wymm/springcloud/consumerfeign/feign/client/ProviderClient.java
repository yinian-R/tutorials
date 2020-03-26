package com.wymm.springcloud.consumerfeign.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider", fallback = ProviderClientFallback.class)
public interface ProviderClient {
    
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHi(@RequestParam String name);
    
}
