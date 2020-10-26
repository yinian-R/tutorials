package com.wymm.httpclient4;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * 最佳实践（还没有在真实华环境使用过）
 */
public class _8BestTest {
    
    @Test
    void useCustomHttpClient() throws InterruptedException {
        String[] toGet = {
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.baidu.com",
                "http://www.github.com",
                "http://www.bing.com"
        };
        
        for (String get : toGet) {
            MultiHttpClientConnThread thread = new MultiHttpClientConnThread(Http.HttpHolder.SINGLETON.client, new HttpGet(get));
            thread.start();
        }
        
        Thread.sleep(10);
    }
    
    
    public static class Http {
        
        private PoolingHttpClientConnectionManager connManager;
        private CloseableHttpClient client;
        private CustomIdleConnectionMonitorThread idleConnectionMonitor;
        
        private Http() {
            connManager = new PoolingHttpClientConnectionManager();
            // 设置打开的连接总数的最大值
            connManager.setMaxTotal(30);
            // 设置每个路由的最大并发连接数
            connManager.setDefaultMaxPerRoute(5);
            
            ConnectionKeepAliveStrategy keepAliveStrategy = (response, context) ->
                    Stream.of(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                            .filter(h -> h.getName().equalsIgnoreCase("timeout") && StringUtils.isNumeric(h.getValue()))
                            .findFirst()
                            .map(h -> NumberUtils.toLong(h.getValue()))
                            .orElse(30L) * 1000;
            
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5 * 1000) // Connection Manager Timeout
                    .setConnectTimeout(5 * 1000) // Connection Timeout
                    .setSocketTimeout(5 * 1000) // Socket Timeout
                    .build();
            
            
            client = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(connManager)
                    .setKeepAliveStrategy(keepAliveStrategy)
                    .build();
            
            
            // 创建一个监视线程以关闭空闲和过期的连接
            idleConnectionMonitor = new CustomIdleConnectionMonitorThread(connManager);
            idleConnectionMonitor.start();
        }
        
        public CloseableHttpClient getClient() {
            return client;
        }
        
        public void shutdown() {
            IOUtils.closeQuietly(client, null);
            IOUtils.closeQuietly(connManager, null);
        }
        
        public static class HttpHolder {
            public final static Http SINGLETON = new Http();
        }
    }
    
    public static class CustomIdleConnectionMonitorThread extends Thread {
        
        private final HttpClientConnectionManager connectionManager;
        private volatile boolean shutdown;
        
        public CustomIdleConnectionMonitorThread(HttpClientConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }
        
        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(1000);
                        connectionManager.closeExpiredConnections();
                        // 使用了 ConnectionKeepAliveStrategy，可以不设置 closeIdleConnections
                        // connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException e) {
                shutdown();
            }
        }
        
        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
    
}
