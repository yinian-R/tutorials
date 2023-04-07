package com.wymm.springboottemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 避免在 flyway 完成初始化之前，Bean 调用 @PostConstruct 造成 table doesn't exist 异常
 */
@Slf4j
@ConditionalOnClass(Flyway.class)
@Configuration
public class FlywayConfig {
    
    /**
     * 每次执行迁移前，修复元数据表
     */
    @Bean
    public FlywayMigrationStrategy customFlywayMigrationStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }
    
}
