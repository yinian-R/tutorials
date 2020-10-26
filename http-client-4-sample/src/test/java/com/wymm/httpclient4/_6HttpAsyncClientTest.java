package com.wymm.httpclient4;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _6HttpAsyncClientTest {
    
    /**
     * 异步请求的简单例子
     */
    @Test
    void whenUseHttpAsyncClient_thenCorrect() throws ExecutionException, InterruptedException {
        CloseableHttpAsyncClient asyncClient = HttpAsyncClients.createDefault();
        asyncClient.start();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        
        Future<HttpResponse> future = asyncClient.execute(httpGet, null);
        HttpResponse response = future.get();
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
    
    /**
     * 使用 HttpAsyncClient 进行多线程
     */
    @Test
    void whenUseMultipleHttpAsyncClient_thenCorrect() throws IOReactorException, InterruptedException {
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        NHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        CloseableHttpAsyncClient client = HttpAsyncClients.custom().setConnectionManager(cm).build();
        client.start();
        
        String[] toGet = {
                "http://www.baidu.com",
                "http://www.github.com",
                "http://www.bing.com"
        };
        
        GetThread[] threads = new GetThread[toGet.length];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpGet = new HttpGet(toGet[i]);
            threads[i] = new GetThread(client, httpGet);
        }
        
        for (GetThread thread : threads) {
            thread.start();
        }
        
        for (GetThread thread : threads) {
            thread.join();
        }
        
    }
    
}
