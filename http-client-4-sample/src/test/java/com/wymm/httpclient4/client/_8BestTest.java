package com.wymm.httpclient4.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 最佳实践（还没有在真实华环境使用过）
 */
@Slf4j
class _8BestTest {
    
    // 其他服务器链接
    private final String url = "http://172.25.1.100:8088";
    
    /**
     * 最佳实践
     */
    @Test
    void useCustomHttpClient() {
        
        HttpClientFactory httpClientFactory = new HttpClientFactory();
        CloseableHttpClient client = httpClientFactory.build();
        
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int size = 50000;
        for (int i = 1; i <= size; i++) {
            MultiHttpClientConnThread thread = new MultiHttpClientConnThread(client, new HttpGet(url));
            executorService.submit(thread);
        }
        
        while (true) {
            if (MultiHttpClientConnThread.count.get() == size) {
                executorService.shutdown();
                break;
            }
        }
        
        httpClientFactory.close();
    }
    
    /**
     * 默认 Client，速度相对较慢
     */
    @Test
    void useDefaultHttpClient() {
        CloseableHttpClient client = HttpClients.custom().build();
        
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int size = 50000;
        for (int i = 1; i <= size; i++) {
            MultiHttpClientConnThread thread = new MultiHttpClientConnThread(client, new HttpGet(url));
            executorService.submit(thread);
        }
        
        while (true) {
            if (MultiHttpClientConnThread.count.get() == size) {
                executorService.shutdown();
                break;
            }
        }
    }
    
}
