package com.wymm.cache.jetcache.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component("JackJsonKeyConvertor")
public class JackJsonKeyConvertor implements Function<Object, Object> {
    
    private static ObjectMapper objectMapper;
    
    public JackJsonKeyConvertor() {
        objectMapper = JacksonUtils.newObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        // 设置默认按自然排序
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    }
    
    @SneakyThrows
    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            return null;
        }
        if (originalKey instanceof String) {
            return originalKey;
        }
        
        String jsonString = objectMapper.writeValueAsString(originalKey);
        jsonString = jsonString.replaceAll(":", "=");
        //jsonString = jsonString.replaceAll("\"", "");
        //jsonString = jsonString.replaceAll(" ", "");
        return jsonString;
    }
    
}
