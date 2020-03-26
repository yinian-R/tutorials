package com.wymm._7bridge;

import com.wymm._7bridge.a.GreenCircle;
import com.wymm._7bridge.a.RedCircle;
import com.wymm._7bridge.b.Circle;
import com.wymm._7bridge.b.Shape;

public class MainDemo {
    public static void main(String[] args) {
        Shape redCircle = new Circle(100, 100, 10, new RedCircle());
        Shape greenCircle = new Circle(100, 100, 10, new GreenCircle());
        redCircle.draw();
        greenCircle.draw();
    }
}
