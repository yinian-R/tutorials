package com.wymm.httpclient4;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * EntityUtils.consume（response.getEntity）调用–消耗掉响应的全部内容，以便管理器可以将连接释放回池
 */
public class MultiHttpClientConnThread extends Thread {
    private CloseableHttpClient client;
    private HttpGet get;
    
    public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get) {
        this.client = client;
        this.get = get;
    }
    
    public void run() {
        try {
            CloseableHttpResponse response = client.execute(get);
            EntityUtils.consumeQuietly(response.getEntity());
        } catch (IOException ignore) {
        }
    }
}
