package com.wymm.httpclient4;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * 一个监视线程，关闭超过30秒空闲和过期的连接
 */
public class IdleConnectionMonitorThread extends Thread {

    private final HttpClientConnectionManager connectionManager;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(1000);
                    connectionManager.closeExpiredConnections();
                    connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
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
