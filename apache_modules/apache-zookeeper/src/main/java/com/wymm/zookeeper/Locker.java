package com.wymm.zookeeper;

import lombok.AllArgsConstructor;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;


public class Locker implements Watcher {
    
    private final ZooKeeper zooKeeper;
    private final String lockPath;
    private final CountDownLatch countDownLatch;
    
    private String createdNode;
    
    public Locker(ZooKeeper zooKeeper, String lockPath, CountDownLatch countDownLatch) {
        this.zooKeeper = zooKeeper;
        this.lockPath = lockPath;
        this.countDownLatch = countDownLatch;
    }
    
    @Override
    public void process(WatchedEvent event) {
        if (event.getType().equals(Event.EventType.NodeDeleted)) {
            getLock();
        }
    }
    
    public void getLock() {
        try {
            if (createdNode == null) {
                createdNode = zooKeeper.create(lockPath + "/lock-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            }
            List<String> children = zooKeeper.getChildren(lockPath, false);
            List<String> lockNodes = children.stream()
                    .sorted().collect(Collectors.toList());
            System.out.println(lockNodes.toString());
            for (int i = 0; i < lockNodes.size(); i++) {
                String node = lockPath + "/" + lockNodes.get(i);
                if (i == 0 && createdNode.equals(node)) {
                    countDownLatch.countDown();
                    break;
                } else if (createdNode.equals(node)) {
                    String watchNode = lockPath + "/" + lockNodes.get(i - 1);
                    if (zooKeeper.exists(watchNode, this) == null) {
                        getLock();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
