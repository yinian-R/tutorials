package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ExtendableBean {
    
    public String name;
    private Map<String, String> properties = new HashMap<>();
    
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
    
    @JsonAnySetter
    public String add(String key, String value) {
        return properties.put(key, value);
    }
}
