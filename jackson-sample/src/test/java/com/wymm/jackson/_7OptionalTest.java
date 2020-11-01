package com.wymm.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.wymm.jackson.optional.Book;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 说明了 Jackson 处理 Optional 当做普通对象处理
 * 需引入依赖 jackson-datatype-jdk8
 */
class _7OptionalTest {
    
    /**
     * 序列化 Optional 类型
     */
    @Test
    void whenSerializeOptional_thenCorrect() throws JsonProcessingException {
        Book book = new Book();
        book.setTitle("Oliver Twist");
        book.setSubTitle(Optional.of("The Parish Boy's Progress"));
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        String jsonString = objectMapper.writeValueAsString(book);
        assertEquals(jsonString, "{\"title\":\"Oliver Twist\",\"subTitle\":\"The Parish Boy's Progress\"}");
    }
    
    /**
     * 反序列化 Optional 类型
     */
    @Test
    void whenDeserializeOptional_thenCorrect() throws JsonProcessingException {
        String jsonString = "{\"title\":\"Oliver Twist\",\"subTitle\":\"The Parish Boy's Progress\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        Book newBook = objectMapper.readValue(jsonString, Book.class);
        
        assertEquals(newBook.getSubTitle(), Optional.of("The Parish Boy's Progress"));
    }
}
