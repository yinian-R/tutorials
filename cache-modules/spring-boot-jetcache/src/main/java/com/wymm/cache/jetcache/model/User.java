package com.wymm.cache.jetcache.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    
    private Long id;
    
    private String name;
    
    private Integer age;
    
}
