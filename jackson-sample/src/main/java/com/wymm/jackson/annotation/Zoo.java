package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Zoo {
    
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "$type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Dog.class, name = "dog"),
            @JsonSubTypes.Type(value = Cat.class, name = "cat")
    })
    public Animal animal;
    
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Animal {
        public String name;
    }
    
    @JsonTypeName("dog")
    public static class Dog extends Animal {
        public double barkVolume;
        
        public Dog() {
        }
        
        public Dog(String name) {
            super(name);
        }
        
    }
    
    @JsonTypeName("cat")
    public static class Cat extends Animal {
        public int lives;
        boolean likesCream;
        
        public Cat() {
        }
    }
    
}
