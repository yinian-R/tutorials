package com.wymm.httpclient4.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RestTemplateRequestThread implements Runnable {
    
    public static final AtomicInteger count = new AtomicInteger();
    private final RestTemplate restTemplate;
    private final String url;
    
    public RestTemplateRequestThread(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }
    
    private static void count() {
        int i = count.incrementAndGet();
        if (i % 1000 == 0) {
            log.info(String.valueOf(i));
        }
    }
    
    @Override
    public void run() {
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
            //System.out.println(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            count();
        }
    }
}
