package com.wymm.springboothttp2client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class SpringBootHttp2ClientApplication implements CommandLineRunner {

    @Value("${sample.server.url}")
    private String url;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootHttp2ClientApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
    
        String forObject = restTemplate.getForObject(url + "/hello", String.class);
        log.info("response str:{}",forObject);
    }
}
