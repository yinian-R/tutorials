package com.wymm.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

public class _1Executor {
    
    
    @Test
    public void execute() {
        Executor executor = new Invoke();
        executor.execute(() -> {
            // task to performed
            System.out.println("hello world");
        });
    }
    
}


class Invoke implements Executor {
    
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}