package com.wymm.greeter.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreeterAutoConfiguration {

    @Bean
    public GreeterBeanFactoryPostProcessor greeterBeanFactoryPostProcessor(){
        return new GreeterBeanFactoryPostProcessor();
    }
}
