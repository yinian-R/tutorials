package com.wymm.concurrent.base.future;

import java.util.concurrent.Callable;

public class FactorialCallableTask implements Callable<Integer> {
    private Integer number;
    
    public FactorialCallableTask(Integer number) {
        this.number = number;
    }
    
    @Override
    public Integer call() throws Exception {
        if(number < 0) {
            throw new IllegalArgumentException("Number should be positive");
        }
        return number;
    }
}
