package com.wymm._5prototype;

public class Rectangle extends Shape {
    
    public Rectangle() {
        type = "Rectangle";
    }
    
    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
