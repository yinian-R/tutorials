package com.wymm._3singleton.hoder;

/**
 * 静态内部类实现（推荐）
 * 线程安全、单例
 */
public class Singleton {
    
    private Singleton() {
    }
    
    public static Singleton getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    private static class SingletonHolder {
        private static final Singleton SINGLETON = new Singleton();
    }
    
}
