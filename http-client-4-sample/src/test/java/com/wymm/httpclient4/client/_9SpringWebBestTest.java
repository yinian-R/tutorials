package com.wymm.httpclient4.client;

import com.wymm.httpclient4.config.RestTemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Spring RestTemplate
 * 使用 {@link RestTemplateConfig} 配置的 RestTemplate
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class _9SpringWebBestTest {
    
    @Autowired
    private RestTemplate restTemplate;
    
    // 其他服务器链接
    private final String url = "http://172.25.1.100:8088";
    
    /**
     * 测试发现默认的 RestTemplate 性能和最佳实践中的 HttpClient 相差无几
     */
    @Test
    void usingCustomRestTemplate() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int size = 50000;
        for (int i = 1; i <= size; i++) {
            executorService.submit(new RestTemplateRequestThread(restTemplate, url));
        }
        
        while (true) {
            if (RestTemplateRequestThread.count.get() == 50000) {
                executorService.shutdown();
                break;
            }
        }
    }
    
    /**
     * 即使使用默认的 RestTemplate 性能也表现极佳。
     * 因为 RestTemplate 默认有连接管理池和连接驱逐线程
     */
    @Test
    void usingDefaultRestTemplate() {
        RestTemplate defaultRestTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int size = 50000;
        for (int i = 1; i <= size; i++) {
            executorService.submit(new RestTemplateRequestThread(defaultRestTemplate, url));
        }
        
        while (true) {
            if (RestTemplateRequestThread.count.get() == 50000) {
                executorService.shutdown();
                break;
            }
        }
    }
    
}
