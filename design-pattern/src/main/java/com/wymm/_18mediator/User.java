package com.wymm._18mediator;

public class User {
    
    String name;
    
    public User(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void sendMessage(String message) {
        ChatRoom.showMessage(this, message);
    }
}
