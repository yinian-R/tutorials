package com.wymm._3singleton.hungry;

/**
 * 饿汉模式
 * 线程安全、单例
 */
public class Singleton {
    
    private static Singleton instance = new Singleton();
    
    private Singleton() {
    }
    
    // 获取对象的方法
    public static Singleton getInstance() {
        return instance;
    }
    
    public void doSomething() {
        // do something
    }
}
