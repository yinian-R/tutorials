package com.wymm.httpclient4.client.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class _GetTest {
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * 使用 UriComponentsBuilder 封装 GET 请求的链接和参数
     */
    @Test
    void usingGet(){
        String url = "http://127.0.0.1:8080";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("page", 1);
        builder.queryParam("pageSize", 5);
    
        String resultData = restTemplate.getForObject(builder.build().toUri(), String.class);
    }
}
