package com.wymm.jackson.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Car extends Vehicle {
    
    private int seatingCapacity;
    private double topSpeed;
    
    public Car() {
    }
    
    public Car(String make, String model, int seatingCapacity, double topSpeed) {
        super(make, model);
        this.seatingCapacity = seatingCapacity;
        this.topSpeed = topSpeed;
    }
}
