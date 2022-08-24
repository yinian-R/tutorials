package com.wymm.springboottemplate.config.scheduled;

import com.wymm.springboottemplate.module.manage.scheduled.DemoScheduled;
import lombok.Data;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("scheduled.demo")
public class DemoScheduledConfig {
    
    public static final String CRON = "${scheduled.demo.cron:-}";
    
    /**
     * 是否开启事件下发
     */
    private Boolean enabled;
    
    /**
     * 事件下发执行 cron 表达式
     */
    private String cron;
    
    public boolean isEnabled() {
        return BooleanUtils.isTrue(enabled);
    }
    
    @Bean
    @ConditionalOnProperty(name = "scheduled.demo.enabled", havingValue = "true")
    public DemoScheduled demoScheduled() {
        return new DemoScheduled();
    }
}
