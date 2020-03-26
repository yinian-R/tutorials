package com.wymm._19memento;

/**
 * 备忘录，负责存储发起人 Originator 的内部状态
 */
public class Memento {
    
    private String state;
    
    public Memento(String state) {
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
}
