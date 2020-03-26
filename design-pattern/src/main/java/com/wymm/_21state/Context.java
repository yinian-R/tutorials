package com.wymm._21state;

public class Context {
    private State state;
    
    public Context() {
        this.state = null;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return "Context{" +
                "state=" + state +
                '}';
    }
}
