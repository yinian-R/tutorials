package com.wymm.springeasyexcelsample.web.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 避免在 flyway 完成初始化之前，Bean 调用 @PostConstruct 造成 table doesn't exist 异常
 */
@Configuration
public class FlywayConfiguration {
    
    
    @Bean
    public FlywayMigrationStrategy customFlywayMigrationStrategy() {
        return flyway1 -> {
            flyway1.repair();
            flyway1.migrate();
        };
    }
    
}
