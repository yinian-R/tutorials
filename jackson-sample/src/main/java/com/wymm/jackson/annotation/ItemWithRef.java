package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ItemWithRef {
    public int id;
    public String itemName;
    
    @JsonManagedReference
    public UserWithRef owner;
}
