package com.wymm.springboothttp2client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Http2ClientTest {
    
    @Value("${sample.server.url}")
    private String url;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Test
    void test() {
        String forObject = restTemplate.getForObject(url + "/hello", String.class);
        log.info("response str:{}", forObject);
    }
    
}
