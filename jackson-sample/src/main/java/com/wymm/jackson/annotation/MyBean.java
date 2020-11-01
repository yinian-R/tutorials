package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MyBean {
    public int id;
    private String name;
    
    public MyBean() {
    }
    
    public MyBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @JsonGetter("name")
    public String getTheName() {
        return name;
    }
    
    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }
    
}
