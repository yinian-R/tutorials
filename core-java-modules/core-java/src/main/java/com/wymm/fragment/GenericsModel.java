package com.wymm.fragment;

/**
 * 泛型类
 * 在此处T可以改为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
 * 通常情况下，T，E，K，V，？ 是这样约定的：
 * ？ 表示不确定的 java 类型
 * T (type) 表示具体的类型
 * K 用于键
 * V 用于值
 * E 代表Element
 * 
 * 在实例化泛型时，必须指定类型
 */
public class GenericsModel<T> {
    
    private T type;
    
    public GenericsModel() {
    }
    
    public GenericsModel(T type) {
        this.type = type;
    }
    
    public T getType() {
        return type;
    }
    
    public void setType(T type) {
        this.type = type;
    }
}
