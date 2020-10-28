package com.wymm.httpclient4.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * EntityUtils.consume（response.getEntity）调用–消耗掉响应的全部内容，以便管理器可以将连接释放回池
 */
@Slf4j
public class MultiHttpClientConnThread extends Thread {
    public static final AtomicInteger count = new AtomicInteger();
    private final CloseableHttpClient client;
    private final HttpUriRequest request;
    
    public MultiHttpClientConnThread(CloseableHttpClient client, HttpUriRequest request) {
        this.client = client;
        this.request = request;
    }
    
    public static void countRequest() {
        int i = count.incrementAndGet();
        if (i % 1000 == 0) {
            log.info(String.valueOf(i));
        }
    }
    
    public void run() {
        try {
            CloseableHttpResponse response = client.execute(request);
            // use response
            EntityUtils.consumeQuietly(response.getEntity());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            countRequest();
        }
    }
}
