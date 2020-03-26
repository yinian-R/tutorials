package com.wymm._10decorator.model;

public abstract class ShapeDecorator implements Shape {
    protected Shape decoratorShape;
    
    public ShapeDecorator(Shape decoratorShape) {
        this.decoratorShape = decoratorShape;
    }
    
    public void draw() {
        this.decoratorShape.draw();
    }
}
