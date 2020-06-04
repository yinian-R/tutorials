package com.wymm.greeter.app;

import com.wymm.greeter.library.GreeterTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication(scanBasePackages = {"com.wymm.greeter.app", "com.wymm.greeter.autoconfigure"})
public class GreeterSpringBootSampleAppApplication implements CommandLineRunner {
    
    @Autowired
    GreeterTemplate greeterTemplate;
    
    public static void main(String[] args) {
        SpringApplication.run(GreeterSpringBootSampleAppApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        String greet = greeterTemplate.greet();
        log.info("greet:{}", greet);
    }
}
