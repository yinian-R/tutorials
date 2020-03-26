package com.wymm._1factory;


import com.wymm._1factory.factory.ShapeFactory;
import com.wymm._1factory.model.Shape;

public class Main {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        // 工厂生成circle对象
        Shape circle = shapeFactory.getShape("circle");
        circle.draw();
        
        // 工厂生成rectangle对象
        Shape rectangle = shapeFactory.getShape("rectangle");
        rectangle.draw();
        
        // 工厂生成square对象
        Shape square = shapeFactory.getShape("Square");
        square.draw();
    }
}
