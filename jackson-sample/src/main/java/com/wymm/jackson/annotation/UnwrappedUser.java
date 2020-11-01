package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UnwrappedUser {
    public int id;
    
    @JsonUnwrapped
    public Name name;
    
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {
        public String firstName;
        public String lastName;
    }
}
