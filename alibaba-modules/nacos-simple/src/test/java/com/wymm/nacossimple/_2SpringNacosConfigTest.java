package com.wymm.nacossimple;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootTest
public class _2SpringNacosConfigTest {
    
    
    @Value("${commen.name}")
    private String name;
    
    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;
    
    /**
     * 读取 Nacos 配置管理中的配置
     * {@link Value}读取的配置不会动态变更
     */
    @Test
    void usingNacosReadConfig() {
        log.info(name);
    }
    
    /**
     * 动态获取 Nacos 配置管理中的配置
     * 使用 {@link ConfigurableApplicationContext} 获取配置
     */
    @Test
    void dynamicConfig(){
        log.info(configurableApplicationContext.getEnvironment().getProperty("commen.name"));
        // 此时修改数据后，重新获取能得到最新值，此处不做演示
    }
}
