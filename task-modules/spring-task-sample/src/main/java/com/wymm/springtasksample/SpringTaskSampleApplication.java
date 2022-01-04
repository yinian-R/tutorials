package com.wymm.springtasksample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringTaskSampleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringTaskSampleApplication.class, args);
    }
    
}
