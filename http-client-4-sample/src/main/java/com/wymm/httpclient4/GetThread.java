package com.wymm.httpclient4;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.nio.client.HttpAsyncClient;

import java.util.concurrent.Future;

@Slf4j
public class GetThread extends Thread {
    
    private HttpAsyncClient client;
    private HttpGet httpGet;
    
    public GetThread(HttpAsyncClient client, HttpGet httpGet) {
        this.client = client;
        this.httpGet = httpGet;
    }
    
    @Override
    public void run() {
        try {
            Future<HttpResponse> future = client.execute(httpGet, null);
            HttpResponse response = future.get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
