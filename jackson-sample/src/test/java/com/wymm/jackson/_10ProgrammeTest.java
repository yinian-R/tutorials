package com.wymm.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wymm.jackson.time.CustomDateDeserializer;
import com.wymm.jackson.time.CustomDateSerializer;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

class _10ProgrammeTest {
    
    /**
     * 使用编程式的一些示例
     */
    @Test
    void example() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 设置命名策略
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        
        // 设置日期转换格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        
        // 设置属性包含策略
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        SimpleModule module = new SimpleModule();
        // 设置序列化器
        module.addSerializer(Date.class, new CustomDateSerializer());
        // 设置反序列化器
        module.addDeserializer(Date.class, new CustomDateDeserializer());
        objectMapper.registerModule(module);
        
        // 序列化
        // 启用漂亮的打印
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 启用日期序列化为时间戳（默认启用）
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // 反序列化
        // 反序列化时，防止在遇到未知属性时发生异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        // 允许不带引号的属性名
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许带单引号的属性名
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }
    
}
