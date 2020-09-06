package com.wymm.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperClient {
    
    public static ZooKeeper connect(String connectString, int sessionTimeout) throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, watchedEvent -> countDownLatch.countDown());
        countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
        return zooKeeper;
    }
    
}
