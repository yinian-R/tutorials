package com.wymm.httpclient4.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpClientConnectionManager;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 一个监视线程，关闭超过 idleTimeSec 秒空闲和过期的连接
 */
@Slf4j
public class IdleConnectionMonitorThread extends Thread implements Closeable {
    
    private final HttpClientConnectionManager connectionManager;
    private int idleTimeSec;
    private volatile boolean shutdown;
    
    public IdleConnectionMonitorThread(HttpClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    
    public IdleConnectionMonitorThread(HttpClientConnectionManager connectionManager, int idleTimeSec) {
        this.connectionManager = connectionManager;
        this.idleTimeSec = idleTimeSec;
    }
    
    @Override
    public void run() {
        try {
            while (shutdown) {
                TimeUnit.MILLISECONDS.sleep(idleTimeSec > 0 ? idleTimeSec : 10);
                connectionManager.closeExpiredConnections();
                if (idleTimeSec > 0) {
                    // 使用了 ConnectionKeepAliveStrategy，可以不设置 closeIdleConnections
                    connectionManager.closeIdleConnections(idleTimeSec, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    @Override
    public void close() {
        shutdown = true;
    }
}
