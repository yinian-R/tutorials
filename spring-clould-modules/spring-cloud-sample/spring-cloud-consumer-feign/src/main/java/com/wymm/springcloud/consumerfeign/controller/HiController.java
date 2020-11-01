package com.wymm.springcloud.consumerfeign.controller;

import com.wymm.springcloud.consumerfeign.feign.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    
    @Autowired
    ProviderClient providerClient;
    
    @RequestMapping("/hi")
    public String Hello(@RequestParam String name) {
        return providerClient.sayHi(name);
    }
    
}
