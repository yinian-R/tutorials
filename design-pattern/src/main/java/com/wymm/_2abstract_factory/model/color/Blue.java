package com.wymm._2abstract_factory.model.color;

public class Blue implements Color {
    
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}