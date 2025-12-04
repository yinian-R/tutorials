package com.example.springwebredis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springwebredis.web.mapper")
public class MybatisConfig {
}
