package com.wymm._14chain_of_responsibility;

public class ConsoleLogger extends AbstractLogger {
    
    public ConsoleLogger(int level) {
        this.level = level;
    }
    
    @Override
    protected void write(String message) {
        System.out.println("Console::Logger: " + message);
    }
}