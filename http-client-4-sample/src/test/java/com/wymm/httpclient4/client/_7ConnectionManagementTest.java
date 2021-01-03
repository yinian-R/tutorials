package com.wymm.httpclient4.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

class _7ConnectionManagementTest {
    
    private final static long DEFAULT_SECONDS = 30L;
    
    /**
     * 使用PoolingHttpClientConnectionManager来获取和管理多线程连接池
     */
    @Test
    void whenUseHttpClientConnectionManagement() throws InterruptedException {
        HttpGet get1 = new HttpGet("/");
        HttpGet get2 = new HttpGet("http://baidu.com");
        // 使用连接池
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client1 = HttpClients.custom().setConnectionManager(connManager).build();
        CloseableHttpClient client2 = HttpClients.custom().setConnectionManager(connManager).build();
        
        MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client1, get1);
        MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client2, get2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
    
    /**
     * 对于许多实际应用程序默认的连接限制可能会过于约束，可以通过 {@link ConnPoolControl} 进行调整
     */
    void listConnectionManagement() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        // 设置打开的连接总数的最大值
        connManager.setMaxTotal(30);
        // 设置每个路由的最大并发连接数
        connManager.setDefaultMaxPerRoute(5);
        // 设置到特定路由的并发连接总数
        HttpRoute httpRoute = new HttpRoute(new HttpHost("www.baidu.com"));
        connManager.setMaxPerRoute(httpRoute, 10);
        
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();
    }
    
    /**
     * 如果响应中不存在Keep-Alive标头，则HttpClient假定可以无限期保持连接。
     * 为了解决这个问题并能够管理死连接，我们需要自定义连接保持策略
     */
    @Test
    void customConnectionKeepAliveStrategy() {
        ConnectionKeepAliveStrategy keepAliveStrategy = (response, context) ->
                Stream.of(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                        .filter(h -> h.getName().equalsIgnoreCase("timeout") && StringUtils.isNumeric(h.getValue()))
                        .findFirst()
                        .map(h -> NumberUtils.toLong(h.getValue()))
                        .orElse(DEFAULT_SECONDS) * 1000;
        
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        
        CloseableHttpClient client = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrategy)
                .setConnectionManager(connManager)
                .build();
    }
    
    /**
     * 设置 Connection Manager 的 Socket Timeout（非阻塞I/O操作的socket超时时间）
     * InputStream 读数据、OutputStream 写数据的时间
     */
    @Test
    void usingSoTimeout() {
        HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
        PoolingHttpClientConnectionManager connManager
                = new PoolingHttpClientConnectionManager();
        connManager.setSocketConfig(route.getTargetHost(), SocketConfig.custom().
                setSoTimeout(5000).build());
    }
    
    /**
     * 连接驱逐，用于检测空闲和过期的连接并关闭它们
     * 有三种方法可以实现这一点
     */
    @Test
    void connectionEviction() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        // 1.定义不活动的时间（毫秒），在此期间后必须通过验证才返回给消费者。这是一个昂贵的选择，并不总是可靠的（不太可靠）
        connManager.setValidateAfterInactivity(30 * 1000);
        
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(
                RequestConfig.custom().build()
        ).setConnectionManager(connManager).build();
        
        // 2.创建一个监视线程以关闭空闲和过期的连接（需编写驱逐连接类，冗余）
        IdleConnectionMonitorThread idleConnectionMonitor = new IdleConnectionMonitorThread(connManager, 5);
        idleConnectionMonitor.start();
        
        // 3. HttpClientBuilder 会根据参数创建一个驱逐连接类（推荐）
        client = HttpClients.custom()
                // 驱逐过期连接
                .evictExpiredConnections()
                .setKeepAliveStrategy(
                        (response, context) -> Arrays.stream(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout") && StringUtils.isNumeric(h.getValue()))
                                .findFirst()
                                .map(h -> NumberUtils.toLong(h.getValue()))
                                .orElse(30L) * 1000
                )
                // 驱逐空闲连接，设置 KeepAliveStrategy 后，不用调用该方法
                //.evictIdleConnections(30, TimeUnit.SECONDS)
                .build();
    }
    
    /**
     * 要正确关闭连接，我们需要执行以下所有操作：
     * <p>
     * 消耗并关闭响应（如果可关闭）
     * 关闭 client
     * 关闭 connection manager
     * <p>
     * 如果在没有关闭连接的情况下关闭了管理器，则将关闭所有连接并释放所有资源。
     * 重要的是要记住，这不会刷新任何可能存在于现有连接中的数据
     * <p>
     * 如果使用连接池，通过EntityUtils.consume()关闭响应的内容，以便管理器可以将连接释放回池
     */
    @Test
    void close() throws IOException {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        HttpGet get = new HttpGet("http://baidu.com");
        CloseableHttpResponse response = client.execute(get);
        
        EntityUtils.consumeQuietly(response.getEntity());
        response.close();
        client.close();
        connManager.close();
    }
}
