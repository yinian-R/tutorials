package com.wymm.zookeeper;

import lombok.AllArgsConstructor;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 一个简单的配置写入类
 */
@AllArgsConstructor
public class ConfigWriter {
    
    private final ZooKeeper zooKeeper;
    private final String configPath;
    
    public void writeConfig(String configData) throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists(configPath, false);
        if (exists == null) {
            zooKeeper.create(configPath, configData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zooKeeper.setData(configPath, configData.getBytes(), -1);
        }
    }
}
