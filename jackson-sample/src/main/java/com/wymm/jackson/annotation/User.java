package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
    public int id;
    public Name name;
    
    @JsonIgnoreType
    @AllArgsConstructor
    public static class Name {
        public String firstName;
        public String lastName;
    }
    
}
