package com.wymm.log4j.logger;

public class MockStackTraceInfo {
    
    public void info() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        System.out.println(stacks[2].getClassName() + "." + stacks[2].getMethodName() + ":" + stacks[2].getLineNumber() + " : ");
    }
}
