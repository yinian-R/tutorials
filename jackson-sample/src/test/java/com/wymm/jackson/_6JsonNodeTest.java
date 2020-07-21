package com.wymm.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 说明了如何将JSON字符串解析为Jackson JsonNode模型以启用JSON对象的结构化处理
 */
class _6JsonNodeTest {
    
    /**
     * 在 JSON 解析为 JsonNode 对象之后，我们还可以使用 Jackson JSON 树模型
     */
    @Test
    void givenTheJsonNode_whenRetrievingDataFromId_thenCorrect()
            throws JsonParseException, IOException {
        String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonString);
        
        // When
        JsonNode jsonNode1 = actualObj.get("k1");
        assertEquals(jsonNode1.textValue(), "v1");
    }
    
    /**
     * 如果出于某种原因需要将其设置为更低的级别，以下实例演示了负责解析 String 的 JsonParser
     */
    @Test
    void givenUsingLowLevelApi_whenParsingJsonStringIntoJsonNode_thenCorrect()
            throws JsonParseException, IOException {
        String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
        
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(jsonString);
        JsonNode jsonNode = mapper.readTree(parser);
        
        assertNotNull(jsonNode);
    }
}
