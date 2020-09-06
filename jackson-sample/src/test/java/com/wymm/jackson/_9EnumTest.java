package com.wymm.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wymm.jackson._enum.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _9EnumTest {
    
    /**
     * 序列化 Enum 默认输出 Name
     */
    @Test
    void whenDefault_thenCorrect() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(Distance.MILE);
        assertEquals(result, "\"MILE\"");
    }
    
    /**
     * Enum 编写 getter 方法，使用 @JsonFormat 把 Enum 当做对象序列化
     */
    @Test
    void usingJsonFormat_thenCorrect() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(Distance2.MILE);
        assertEquals(result, "{\"unit\":\"miles\",\"meters\":1609.34}");
    }
    
    /**
     * 使用 @JsonValue 设置序列化的方法
     */
    @Test
    void usingJsonValue_thenCorrect() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(Distance3.MILE);
        assertEquals(result, "1609.34");
    }
    
    /**
     * 使用自定义序列化器进行序列化操作
     */
    @Test
    void usingCustomSerializer_thenCorrect() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(Distance4.MILE);
        assertEquals(result, "{\"name\":\"MILE\",\"unit\":\"miles\",\"meters\":1609.34}");
    }
    
    /**
     * 默认情况下，Jackson 默认通过名称反序列化 Enum
     */
    @Test
    void whenDefaultDeserialize_thenCorrect() throws JsonProcessingException {
        String jsonString = "{\"distance\":\"KILOMETER\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        City city = objectMapper.readValue(jsonString, City.class);
        assertEquals(Distance.KILOMETER, city.getDistance());
    }
    
    /**
     * 使用 @JsonValue 设置反序列化的方法
     */
    @Test
    void whenDeserializeUsingJsonValue_thenCorrect() throws JsonProcessingException {
        String jsonString = "{\"distance\":\"0.0254\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        City3 city = objectMapper.readValue(jsonString, City3.class);
        assertEquals(Distance3.INCH, city.getDistance());
    }
}
