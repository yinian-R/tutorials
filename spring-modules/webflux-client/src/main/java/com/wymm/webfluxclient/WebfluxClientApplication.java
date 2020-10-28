package com.wymm.webfluxclient;

import com.wymm.core.interfaces.ProxyCreator;
import com.wymm.core.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxClientApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WebfluxClientApplication.class, args);
    }
    
}
