package com.example.flywaysimple.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 避免在 flyway 完成初始化之前，Bean 调用 @PostConstruct 造成 table doesn't exist 异常
 */
@Configuration
public class FlywayConfiguration {
    
    
    @ConditionalOnProperty(name = "spring.flyway.beforeMigrate", havingValue = "repair")
    @Bean
    public FlywayMigrationStrategy customFlywayMigrationStrategy(Flyway flyway) {
        return new CustomFlywayMigrationStrategy(flyway);
    }
    
}
