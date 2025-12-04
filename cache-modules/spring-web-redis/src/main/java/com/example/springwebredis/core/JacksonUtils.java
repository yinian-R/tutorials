package com.example.springwebredis.core;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 定义一个常量 ObjectMapper。
 * <p>
 * 特别提醒：代码上未限制修改配置，但是在使用过程中请不要修改配置。若需修改配置，请自己手动创建一个新的ObjectMapper
 *
 */
public abstract class JacksonUtils {

    private static final ObjectMapper OM;

    static {
        OM = newObjectMapper();
    }

    public static ObjectMapper json() {
        return OM;
    }

    public static ObjectMapper getObjectMapper() {
        return OM;
    }

    public static ObjectMapper newObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 设置时间序列化器和反序列化
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Long.class, ToStringSerializer.instance);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));

        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        // 设置属性包含策略
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 反序列化时，防止在遇到未知属性时发生异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        // 允许带单引号的属性名
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        objectMapper.registerModules(javaTimeModule);
        return objectMapper;
    }

}
