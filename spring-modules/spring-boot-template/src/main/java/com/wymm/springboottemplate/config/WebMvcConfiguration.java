package com.wymm.springboottemplate.config;

import cn.hutool.core.date.DatePattern;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
        registrar.registerFormatters(registry);
    }
    
    
}