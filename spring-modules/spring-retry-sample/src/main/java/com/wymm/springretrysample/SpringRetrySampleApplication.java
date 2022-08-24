package com.wymm.springretrysample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringRetrySampleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringRetrySampleApplication.class, args);
    }
    
}
