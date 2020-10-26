package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Item {
    public int id;
    public String itemName;
    public User owner;
    
    @JsonIgnoreType
    public static class MyMixInForIgnoreType {
    }
}