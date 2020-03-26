package com.wymm._2abstract_factory.factory;


import com.wymm._2abstract_factory.model.color.Color;
import com.wymm._2abstract_factory.model.shape.Shape;

/**
 * 创建抽象类来获取工厂
 */
public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    
    public abstract Shape getShape(String shape);
}
