package com.wymm.zookeeper;

import lombok.AllArgsConstructor;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 一个简单的配置读取类
 */
@AllArgsConstructor
public class ConfigReader implements Watcher {
    
    private final ZooKeeper zooKeeper;
    private final String configPath;
    
    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDataChanged) {
            readConfig();
        }
    }
    
    public void readConfig() {
        try {
            byte[] data = zooKeeper.getData(configPath, this, null);
            System.out.println(new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
