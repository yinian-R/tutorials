package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserWithIdentity {
    public int id;
    public String name;
    public List<ItemWithIdentity> userItems = new ArrayList<>();
    
    public UserWithIdentity(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public void addItem(ItemWithIdentity item) {
        userItems.add(item);
    }
}