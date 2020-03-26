package com.wymm._19memento;

/**
 * 发起人，负责创建备忘录 Memento，用以记录当前内部状态，并且可以恢复内部状态
 */
public class Originator {
    private int id;
    private String name;
    private String state;
    
    public Originator(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Memento saveStateToMemento() {
        return new Memento(state);
    }
    
    public void recoveryStateToMemento(Memento memento) {
        state = memento.getState();
    }
}
