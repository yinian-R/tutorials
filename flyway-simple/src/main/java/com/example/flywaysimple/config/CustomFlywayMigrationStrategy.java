package com.example.flywaysimple.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;


public class CustomFlywayMigrationStrategy implements FlywayMigrationStrategy {
    
    private final Flyway flyway;
    
    public CustomFlywayMigrationStrategy(Flyway flyway) {
        this.flyway = flyway;
    }
    
    @Override
    public void migrate(Flyway flyway) {
        this.flyway.repair();
        this.flyway.migrate();
    }
    
}
