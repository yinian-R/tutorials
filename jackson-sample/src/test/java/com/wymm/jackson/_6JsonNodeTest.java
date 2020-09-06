package com.wymm.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

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
    
    /**
     * 使用 JsonNode 把 JSON 转换成 YAML
     */
    @Test
    void whenGivenJsonNode_thenPrintYaml() throws IOException {
        URL resource = this.getClass().getResource("/exmple.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(resource);
        
        String yamlStr = toYaml(jsonNode);
        System.out.println(yamlStr);
    }
    
    private String toYaml(JsonNode root) {
        StringBuilder yamlBuilder = new StringBuilder();
        processNode(root, yamlBuilder, 0);
        return yamlBuilder.toString();
    }
    
    private void appendNodeToYaml(JsonNode jsonNode, StringBuilder yamlBuilder, int depth, boolean isArrayItem) {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        boolean isFirst = true;
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> jsonField = fields.next();
            addFieldNameToYaml(yamlBuilder, jsonField.getKey(), depth, isArrayItem && isFirst);
            processNode(jsonField.getValue(), yamlBuilder, depth + 1);
            isFirst = false;
        }
    }
    
    private void processNode(JsonNode jsonNode, StringBuilder yamlBuilder, int depth) {
        if (jsonNode.isValueNode()) {
            yamlBuilder.append(jsonNode.asText());
        } else if (jsonNode.isArray()) {
            for (JsonNode arrayItem : jsonNode) {
                appendNodeToYaml(arrayItem, yamlBuilder, depth, true);
            }
        } else if (jsonNode.isObject()) {
            appendNodeToYaml(jsonNode, yamlBuilder, depth, false);
        }
    }
    
    private void addFieldNameToYaml(StringBuilder yamlBuilder, String fieldName, int depth, boolean isFirstInArray) {
        if (yamlBuilder.length() > 0) {
            yamlBuilder.append("\n");
            int requiredDepth = isFirstInArray ? depth - 1 : depth;
            for (int i = 0; i < requiredDepth; i++) {
                yamlBuilder.append("  ");
            }
            if (isFirstInArray) {
                yamlBuilder.append("- ");
            }
        }
        
        yamlBuilder.append(fieldName);
        yamlBuilder.append(": ");
    }
    
}
