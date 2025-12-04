package com.wymm.cache.jetcache.core;

import com.alicp.jetcache.anno.SerialPolicy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

@Slf4j
@Component("JackJsonSerialPolicy")
public class JackJsonSerialPolicy implements SerialPolicy {
    
    private ObjectMapper objectMapper;
    
    public JackJsonSerialPolicy() {
        objectMapper = JacksonUtils.newObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 序列化结果中包含 @class 字段记录类型信息
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
    }
    
    @Override
    public Function<Object, byte[]> encoder() {
        return v-> {
            try {
                String target = objectMapper.writeValueAsString(v);
                return target.getBytes();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    @Override
    public Function<byte[], Object> decoder() {
        return v-> {
            try {
                Object target = objectMapper.readerFor(Object.class).readValue(v);
                return target;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
    
}
