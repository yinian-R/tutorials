package com.wymm._18mediator;

public class MainDemo {
    public static void main(String[] args) {
        User john = new User("John");
        User robert = new User("Robert");
        
        john.sendMessage("Hi,Robert");
        robert.sendMessage("Hi,John");
    }
}
