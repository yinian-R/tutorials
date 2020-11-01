package com.wymm.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class WebfluxApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }
    
}
