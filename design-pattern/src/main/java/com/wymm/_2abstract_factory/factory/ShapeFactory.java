package com.wymm._2abstract_factory.factory;


import com.wymm._2abstract_factory.model.color.Color;
import com.wymm._2abstract_factory.model.shape.Circle;
import com.wymm._2abstract_factory.model.shape.Rectangle;
import com.wymm._2abstract_factory.model.shape.Shape;
import com.wymm._2abstract_factory.model.shape.Square;

/**
 * 工厂，拓展了抽象工厂类，
 */
public class ShapeFactory extends AbstractFactory {
    
    // 仅实现不错处理，将由另外一个工厂使用
    @Override
    public Color getColor(String color) {
        return null;
    }
    
    @Override
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("Circle")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("Rectangle")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("Square")) {
            return new Square();
        }
        return null;
    }
}
