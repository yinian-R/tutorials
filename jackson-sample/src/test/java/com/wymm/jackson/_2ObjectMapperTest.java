package com.wymm.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wymm.jackson.model.Car;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ObjectMapper类的一个基本功能是能够注册自定义序列化器和反序列化器
 * 在输入输出JSON相应的结构不同于必须对其序列化和反序列化的Java类结构时，自定义序列化器和自定义反序列化器非常有用。
 */
class _2ObjectMapperTest {
    
    /**
     * 使用 writeValue API 将 Java 对象序列化为 JSON 输出
     */
    @Test
    void whenWriteValue() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");
        // 输出文件
        objectMapper.writeValue(new File("target/car.json"), car);
        // 输出成String字符串
        String jsonStr = objectMapper.writeValueAsString(car);
    }
    
    /**
     * 以将JSON字符串解析为JsonNode对象，并用于从特定节点检索数据
     */
    @Test
    void whenReadTree() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();
    }
    
    /**
     * 使用 TypeReference 将数组形式的JSON解析为Java对象列表
     */
    @Test
    void useTypeReference_readJson_toList() throws JsonProcessingException {
        String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>() {
        });
        assertEquals(listCar.size(), 2);
    }
    
    /**
     * 使用 TypeReference 将JSON解析为指定类型Java Map
     */
    @Test
    void useTypeReference_readJson_toMap() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        assertEquals(map.keySet().size(), 2);
    }
    
    /**
     * 在将JSON转换成Java对象时，如果JSON字符串中具有新的字段，则默认过程将导致异常
     * 通过 configure 方法，我们可以扩展默认过程以忽略新字段
     * <p>
     * 也可以转换为 JsonNode，不需要考虑新字段问题
     */
    @Test
    void useConfigure() throws JsonProcessingException {
        String jsonString = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = objectMapper.readValue(jsonString, Car.class);
        
        JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
        JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        String year = jsonNodeYear.asText();
    }
    
    /**
     * 使用自定义 Jackson 序列化器
     */
    @Test
    void useCustomCarSerializer() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(new Car.CustomCarSerializer());
        objectMapper.registerModule(module);
        
        Car car = new Car("yellow", "renault");
        String s = objectMapper.writeValueAsString(car);
        assertEquals(s, "{\"car_brand\":\"renault\"}");
    }
    
    /**
     * 使用自定义 Jackson 反序列化器
     */
    @Test
    void useCustomCarDeserializer() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Car.class, new Car.CustomCarDeserializer());
        objectMapper.registerModule(module);
        
        Car car = objectMapper.readValue(json, Car.class);
        assertNotNull(car.getColor());
        assertNull(car.getType());
    }
    
}
