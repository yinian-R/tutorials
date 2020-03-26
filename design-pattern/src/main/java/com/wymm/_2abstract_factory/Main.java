package com.wymm._2abstract_factory;


import com.wymm._2abstract_factory.factory.AbstractFactory;
import com.wymm._2abstract_factory.factory.producer.FactoryProducer;
import com.wymm._2abstract_factory.model.color.Color;
import com.wymm._2abstract_factory.model.shape.Shape;

public class Main {
    public static void main(String[] args) {
        // 获取shape factory
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        // Circle
        Shape circle = shapeFactory.getShape("Circle");
        circle.draw();
        // Rectangle
        Shape rectangle = shapeFactory.getShape("Rectangle");
        rectangle.draw();
        // Square
        Shape square = shapeFactory.getShape("Square");
        square.draw();
        
        // 获取color factory
        AbstractFactory colorFactory = FactoryProducer.getFactory("color");
        Color red = colorFactory.getColor("red");
        red.fill();
        Color green = colorFactory.getColor("green");
        green.fill();
        Color blue = colorFactory.getColor("blue");
        blue.fill();
    }
}
