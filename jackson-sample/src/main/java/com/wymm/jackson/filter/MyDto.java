package com.wymm.jackson.filter;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("myFilter")
public class MyDto {
    private int intValue;
    
    public MyDto() {
        super();
    }
    
    public int getIntValue() {
        return intValue;
    }
    
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}