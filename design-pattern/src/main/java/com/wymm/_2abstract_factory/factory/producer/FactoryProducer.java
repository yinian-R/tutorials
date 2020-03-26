package com.wymm._2abstract_factory.factory.producer;

import com.wymm._2abstract_factory.factory.AbstractFactory;
import com.wymm._2abstract_factory.factory.ColorFactory;
import com.wymm._2abstract_factory.factory.ShapeFactory;

/**
 * 创建一个工厂生成器类,通过传递属性获取工厂
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("shape")) {
            return new ShapeFactory();
        } else if (choice.equalsIgnoreCase("color")) {
            return new ColorFactory();
        }
        return null;
    }
}
