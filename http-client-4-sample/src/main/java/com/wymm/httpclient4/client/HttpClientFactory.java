package com.wymm.httpclient4.client;

import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class HttpClientFactory implements Closeable {
    
    @Setter
    private PoolingHttpClientConnectionManager connManager;
    
    @Setter
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    
    @Setter
    private RequestConfig requestConfig;
    
    private List<Closeable> closeables;
    
    public CloseableHttpClient build() {
        if (connManager == null) {
            connManager = new PoolingHttpClientConnectionManager();
            // 设置打开的连接总数的最大值
            connManager.setMaxTotal(100);
            // 设置每个路由的最大并发连接数
            connManager.setDefaultMaxPerRoute(10);
            addCloseable(connManager);
        }
        
        if (keepAliveStrategy == null) {
            keepAliveStrategy = (response, context) ->
                    Stream.of(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                            .filter(h -> h.getName().equalsIgnoreCase("timeout") && StringUtils.isNumeric(h.getValue()))
                            .findFirst()
                            .map(h -> NumberUtils.toLong(h.getValue()))
                            .orElse(30L) * 1000;
        }
        
        if (requestConfig == null) {
            requestConfig = RequestConfig.custom()
                    //.setConnectionRequestTimeout(5 * 1000) // Connection Manager Timeout
                    .setConnectTimeout(5 * 1000) // Connection Timeout
                    .setSocketTimeout(10 * 1000) // Socket Timeout
                    .build();
        }
    
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connManager)
                .setKeepAliveStrategy(keepAliveStrategy)
                // 驱逐过期连接
                .evictExpiredConnections()
                .evictIdleConnections(30, TimeUnit.SECONDS)
                .build();
        addCloseable(client);
        return client;
    }
    
    private void addCloseable(Closeable closeable) {
        if (closeables == null) {
            closeables = new ArrayList<>();
        }
        closeables.add(closeable);
    }
    
    @Override
    public void close() {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                IOUtils.closeQuietly(closeable, null);
            }
        }
    }
    
}

