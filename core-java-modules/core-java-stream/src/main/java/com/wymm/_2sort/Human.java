package com.wymm._2sort;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Human {
    private String name;
    private int age;
    
    /**
     * 根据名称和性别进行比较
     */
    public static int compareByNameThenAge(Human h1, Human h2) {
        if (h1.getName().equals(h2.getName())) {
            return h1.getAge() - h2.getAge();
        } else {
            return h1.getName().compareTo(h2.getName());
        }
    }
}
