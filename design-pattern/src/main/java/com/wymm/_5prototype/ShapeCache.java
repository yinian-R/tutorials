package com.wymm._5prototype;

import java.util.HashMap;

public class ShapeCache {
    private static HashMap<String, Shape> shapeMap = new HashMap<>();
    
    public static Shape getShape(String id) {
        Shape shape = shapeMap.get(id);
        return (Shape) shape.clone();
    }
    
    /**
     * 测试添加数据
     */
    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put(circle.getId(), circle);
        
        Square square = new Square();
        square.setId("2");
        shapeMap.put(square.getId(), square);
        
        Rectangle rectangle = new Rectangle();
        rectangle.setId("3");
        shapeMap.put(rectangle.getId(), rectangle);
    }
}
