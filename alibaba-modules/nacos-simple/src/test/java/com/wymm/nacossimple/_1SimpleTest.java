package com.wymm.nacossimple;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class _1SimpleTest {
    
    /**
     * 获取服务端的信息
     * 需提前去 nacos 新建配置：nacos-simple-demo.yml，添加属性 url: 127.0.0.1
     */
    @Test
    void simple() throws NacosException {
        String serverAddr = "127.0.0.1:18848";
        String dataId = "nacos-simple-demo.yml";
        String group = "DEFAULT_GROUP";
        
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig(dataId, group, 5000);
        System.out.println(config);
    }
    
    /**
     * 指定 namespace
     * namespace 不能写命名空间名称，需使用命名空间ID
     */
    @Test
    void usingNamespace() throws NacosException {
        String serverAddr = "127.0.0.1:18848";
        String namespace = "9cb309c5-7239-4a57-91f7-b9616f68c158";
        String dataId = "nacos-simple-demo.yml";
        String group = "DEFAULT_GROUP";
        
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig(dataId, group, 5000);
        System.out.println(config);
    }
    
    /**
     * 监听配置变化
     */
    @Test
    void usingListener() throws NacosException, InterruptedException {
        String serverAddr = "127.0.0.1:18848";
        String dataId = "nacos-simple-demo.yml";
        String group = "DEFAULT_GROUP";
        
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig(dataId, group, 5000);
        System.out.println(config);
        
        // 监听配置变化
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }
            
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("收到配置变化通知:" + configInfo);
            }
        });
        
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}
