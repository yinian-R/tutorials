package com.wymm.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class _1ZookeeperTest {
    
    public static final String CONFIGURATION = "/configuration";
    public static final String GROUPS = "/groups";
    public static final String LOCKS = "/locks";
    
    @BeforeAll
    static void setup() {
        try {
            checkOrCreateNodePath(CONFIGURATION);
            checkOrCreateNodePath(GROUPS);
            checkOrCreateNodePath(LOCKS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void checkOrCreateNodePath(String nodePath) throws KeeperException, InterruptedException, IOException {
        ZooKeeper zookeeper = ZookeeperClient.connect("localhost", 10000);
        Stat exists = zookeeper.exists(nodePath, false);
        if (exists == null) {
            zookeeper.create(nodePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }
    
    /**
     * 这个方法和执行命令 create /data hello world 一样
     * 这里存在一个Bug，new ZooKeeper()只是向 Zookeeper 发起连接请求，此时连接没有创建成功，如果在连接创建成功前调用 create() 方法会抛出 ConnectionLossException
     */
    @Test
    void createZNode_failMethod() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost", 5000, null);
        zooKeeper.create("/data", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.delete("/data", 0);
        zooKeeper.close();
    }
    
    /**
     * 正确的方式应该使用 Watch + CountDownLatch
     * void process(WatchedEvent watchedEvent) 在连接成功的时候才会调用
     */
    @Test
    void createZNode_successMethod() throws IOException, KeeperException, InterruptedException {
        int sessionTimeout = 5000;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("localhost", sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
        zooKeeper.create("/data", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.delete("/data", 0);
        zooKeeper.close();
    }
    
    /**
     * # 配置管理
     * 写入配置
     */
    @Test
    void writeConfig() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zookeeper = ZookeeperClient.connect("localhost", 10000);
        ConfigWriter configuration = new ConfigWriter(zookeeper, CONFIGURATION);
        configuration.writeConfig("hello");
        zookeeper.close();
    }
    
    /**
     * # 配置管理
     * 获取配置，并在调用 getData() 方法获取配置数据时注册这个 Watch，这样可以在节点数据发生变动时得到通知，得到通知之后，我们重新获取配置数据，并重新注册 Watch
     */
    @Test
    void readConfig() throws IOException, InterruptedException {
        ZooKeeper zookeeper = ZookeeperClient.connect("localhost", 10000);
        ConfigReader reader = new ConfigReader(zookeeper, CONFIGURATION);
        reader.readConfig();
        Thread.sleep(Long.MAX_VALUE);
        // set /configuration world
    }
    
    /**
     * 集群管理
     * 集群选主
     * 在节点 /groups 下创建 EPHEMERAL_SEQUENTIAL 类型的子节点，比如 member-1，并对父节点 /groups 设置 Watch 子节点的变换。
     */
    @Test
    void groups() throws IOException, InterruptedException {
        ZooKeeper zookeeper = ZookeeperClient.connect("localhost", 10000);
        GroupMember member = new GroupMember(zookeeper, GROUPS);
        member.join("member");
        member.list();
        Thread.sleep(Long.MAX_VALUE);
        // create /groups/member0000000001 -e
    }
    
    /**
     * 分布式锁
     */
    @Test
    void locks() throws IOException, InterruptedException {
        ZooKeeper zookeeper = ZookeeperClient.connect("localhost", 10000);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Locker locker = new Locker(zookeeper, LOCKS, countDownLatch);
        locker.getLock();
        countDownLatch.await();
        System.out.println("do something");
    }
    
}
