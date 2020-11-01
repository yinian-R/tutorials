package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BeanWithIgnore2 {
    @JsonIgnore
    public int id;
    
    public String name;
    
}
