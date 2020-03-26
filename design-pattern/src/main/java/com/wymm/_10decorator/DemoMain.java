package com.wymm._10decorator;

import com.wymm._10decorator.model.Circle;
import com.wymm._10decorator.model.Rectangle;
import com.wymm._10decorator.model.RedShapeDecorator;
import com.wymm._10decorator.model.Shape;

public class DemoMain {
    public static void main(String[] args) {
        Shape circle = new Circle();
        circle.draw();
        System.out.println();
        
        Shape redCircle = new RedShapeDecorator(new Circle());
        redCircle.draw();
        System.out.println();
        
        Shape redRectangle = new RedShapeDecorator(new Rectangle());
        redRectangle.draw();
        System.out.println();
    }
}
