package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class JsonPropertyBean {
    
    public int id;
    private String name;
    
    @JsonProperty("name")
    public String getTheName() {
        return name;
    }
    
    @JsonProperty("name")
    public void setTheName(String name) {
        this.name = name;
    }
}
