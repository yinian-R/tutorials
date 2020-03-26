package com.wymm._1factory.factory;

import com.wymm._1factory.model.Circle;
import com.wymm._1factory.model.Rectangle;
import com.wymm._1factory.model.Shape;
import com.wymm._1factory.model.Square;

/**
 * 工厂
 */
public class ShapeFactory {
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
