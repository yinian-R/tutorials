package com.wymm._3singleton.enumImpl;

/**
 * 使用枚举实现多个单例（推荐）
 * 线程安全、单例
 * 推荐理由：不需要构造函数、更简洁、支持多个单例在同一个类实现
 */
public enum MultiSingletonEnum {
    ONE_SINGLETON {
        @Override
        public void doSomething() {
            // do something
        }
    },
    TWO_SINGLETON {
        @Override
        public void doSomething() {
            // do something
        }
    };
    
    public abstract void doSomething();
    
}
