package com.wymm.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

class _1ExecutorTest {
    
    
    @Test
    void execute() {
        Executor executor = new Invoke();
        executor.execute(() -> {
            // task to performed
            System.out.println("hello world");
        });
    }
    
    static class Invoke implements Executor {
        
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}


