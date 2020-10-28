package com.wymm.httpclient4.client;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _1HttpClientTest {
    
    /**
     * 访问响应的状态行以及隐式的状态代码
     */
    @Test
    void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response = client.execute(new HttpGet("http://127.0.0.1:8080"));
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(statusCode, HttpStatus.SC_OK);
    }
    
}
