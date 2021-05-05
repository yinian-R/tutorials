package com.wymm.springquartzsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringQuartzSampleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringQuartzSampleApplication.class, args);
    }
    
}
