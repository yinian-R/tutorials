package com.wymm.jackson.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Vehicle {
    private String make;
    private String model;
    
    protected Vehicle() {
    }
    
    protected Vehicle(String make, String model) {
        this.make = make;
        this.model = model;
    }
    
}
