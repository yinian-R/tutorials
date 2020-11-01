package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

public class UserWithRef {
    public int id;
    public String name;
    
    @JsonBackReference
    public List<ItemWithRef> userItems = new ArrayList<>();
    
    public UserWithRef() {
    }
    
    public UserWithRef(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public void addItem(ItemWithRef userItem) {
        this.userItems.add(userItem);
    }
}
