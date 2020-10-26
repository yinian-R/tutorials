package com.wymm.httpclient4;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 讨论了如何配置HttpClient可用的各种类型的超时。它还说明了用于使正在进行的HTTP连接硬超时的简单机制
 * <p>
 * Connect Timeout和DNS Round Robin 需要注意的事
 * 某些较大的域将使用 DNS 轮训配置，本质是将域映射多个IP地址。这给针对此类域的超时提出了新的挑战，这仅仅是因为HttpClient尝试连接到该域超时的方式：
 * HttpClient获取到该域的IP路由列表
 * 它尝试第一个 -超时（使用我们配置的超时）
 * 它尝试第二个 -也超时
 * 等等 …
 * 因此，如您所见– 当我们期望整体操作不会超时时。相反，当所有可能的路由都超时时，它将超时。更重要的是，这将对客户端完全透明地进行（除非您在DEBUG级别配置了日志）
 */
public class _2TimeoutTest {
    
    /**
     * 4.3中提供了正确的方法来设置高级别的超时
     * - Connection Timeout : 建立与远程主机连接的时间
     * - Socket Timeout : 等待数据的时间，两个数据包之间不活动的最长时间
     * - Connection Manager Timeout : 等待连接管理器/池连接的时间
     * 前两个参数最重要。但是在高负载情况下，设置 Connection Manager Timeout 绝对重要，这就是为什么不忽略第三个参数的原因
     */
    @Test
    void usingRequestConfigSetHttpTimeout() {
        int timeout = 5;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout * 1000) // Connection Manager Timeout
                .setConnectTimeout(timeout * 1000) // Connection Timeout
                .setSocketTimeout(timeout * 1000) // Socket Timeout
                .build();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
    
    /**
     * 硬超时
     * 虽然在建立HTTP连接时设置超时并且不接收数据非常有用，但有时我们需要为整个请求设置一个硬超时
     * 例如：下载潜在大文件就属于此类。在这种情况下，可以成功建立连接，可以始终通过数据，但是我们依然需要确保操作不会超过某个特定的时间阈值
     * HttpClient 没有任何配置可以设置请求的整体超时；但是，它提供了请求的中止功能，因此我们可以利用该机制来实现简单的超时机制
     */
    @Test
    void hardTimeout() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080");
        
        int hardTimeout = 5;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                httpGet.abort();
            }
        };
        
        new Timer(true).schedule(timerTask, hardTimeout * 5);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());
    }
    
}
