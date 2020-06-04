package com.wymm.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wymm.jackson.model.MyValue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class _1JacksonTest {
    
    @Test
    void test() throws IOException {
        // read file
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/data.json");
        MyValue myValue = objectMapper.readValue(file, MyValue.class);
        System.out.println(myValue.toString());
    
        // read url
        URL resource = this.getClass().getResource("/data.json");
        MyValue myValue2 = objectMapper.readValue(resource, MyValue.class);
        System.out.println(myValue2.toString());
    }
}
