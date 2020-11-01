package com.wymm.optional;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class _3OptionalMapAndFlatMapTest {
    
    /**
     * 使用map()转换值
     */
    @Test
    public void map() {
        String password = " password ";
        
        Optional<String> optional = Optional.of(password);
        
        boolean correctPassword = optional
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertFalse(correctPassword);
        
        
        correctPassword = optional
                .map(String::trim)
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertTrue(correctPassword);
    }
    
    /**
     * 使用flatMap()转换Optional包装的值并对其解包
     */
    @Test
    public void flatMap() {
        Person person = new Person("john", 20);
        String name = Optional.of(person)
                .flatMap(Person::getName).orElse("");
        assertEquals("john", name);
    }
    
    @AllArgsConstructor
    private static class Person {
        private String name;
        private int age;
        
        public Optional<String> getName() {
            return Optional.of(name);
        }
        
        public Optional<Integer> getAge() {
            return Optional.of(age);
        }
        
    }
    
}
