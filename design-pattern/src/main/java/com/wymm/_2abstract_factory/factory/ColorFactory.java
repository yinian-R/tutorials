package com.wymm._2abstract_factory.factory;


import com.wymm._2abstract_factory.model.color.Blue;
import com.wymm._2abstract_factory.model.color.Color;
import com.wymm._2abstract_factory.model.color.Green;
import com.wymm._2abstract_factory.model.color.Red;
import com.wymm._2abstract_factory.model.shape.Shape;

public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equalsIgnoreCase("RED")) {
            return new Red();
        } else if (color.equalsIgnoreCase("GREEN")) {
            return new Green();
        } else if (color.equalsIgnoreCase("BLUE")) {
            return new Blue();
        }
        return null;
    }
    
    @Override
    public Shape getShape(String shape) {
        return null;
    }
}
