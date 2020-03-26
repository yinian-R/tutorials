package com.wymm._3singleton.enumImpl;

/**
 * 枚举实现（推荐）
 * 线程安全、单例
 * 推荐理由：不需要构造函数，更简洁
 */
public enum SingletonEnum {
    INSTANCE{
        public void doSomething() {
            // do something
        }
    }
    
}
