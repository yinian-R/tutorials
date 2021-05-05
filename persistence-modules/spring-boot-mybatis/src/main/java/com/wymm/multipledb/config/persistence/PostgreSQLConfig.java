package com.wymm.multipledb.config.persistence;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
@MapperScan(basePackages = "com.wymm.multipledb.web.repository.postgresql", sqlSessionTemplateRef = "postgresqlSqlSessionTemplate")
public class PostgreSQLConfig {
    
    @Bean
    @ConfigurationProperties(prefix = "spring.postgresql-datasource")
    public DataSourceProperties postgresqlDataSourceProperties() {
        return new DataSourceProperties();
    }
    
    @Bean
    public DataSource postgresqlDataSource() {
        return postgresqlDataSourceProperties().initializeDataSourceBuilder().build();
    }
    
    @Bean
    public SqlSessionFactory postgresqlSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(postgresqlDataSource());
        factory.setVfs(SpringBootVFS.class);
        return factory.getObject();
    }
    
    @Bean
    public PlatformTransactionManager postgresqlTransactionManager() {
        return new DataSourceTransactionManager(postgresqlDataSource());
    }
    
    @Bean
    public SqlSessionTemplate postgresqlSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(postgresqlSqlSessionFactory());
    }
    
    
}
