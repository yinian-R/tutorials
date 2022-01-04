package com.wymm.springresttemplate.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static com.wymm.springresttemplate.client.HttpProperties.URL;

@SpringBootTest
class _3LoggingInterceptorTest {
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * 测试日志打印
     * 日志级别需设置为 debug
     */
    @Test
    void usingLoggingInterceptor() {
        String forObject = restTemplate.getForObject(URL + "/testMsg", String.class);
    }
    
}
