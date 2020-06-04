package com.wymm.concurrent.base;

import org.junit.jupiter.api.parallel.Execution;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class _8ThreadFactory {
    
    public static void main(String[] args) {
        Executors.defaultThreadFactory();
    }
    
    class SimpleThreadFactory {
    }
}
