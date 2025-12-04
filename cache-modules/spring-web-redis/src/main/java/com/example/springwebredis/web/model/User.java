package com.example.springwebredis.web.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    
    private BigDecimal age;
    
    public User() {
    }
    
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public User(Long id, String name, BigDecimal age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
