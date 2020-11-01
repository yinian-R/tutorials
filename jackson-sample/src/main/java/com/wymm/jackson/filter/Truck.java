package com.wymm.jackson.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Truck extends Vehicle {
    private double payloadCapacity;
    
    public Truck() {
    }
    
    public Truck(String make, String model, double payloadCapacity) {
        super(make, model);
        this.payloadCapacity = payloadCapacity;
    }
}
