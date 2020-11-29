package com.wymm.concurrent.base.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * 计算阶乘任务类
 */
@Slf4j
public class FactorialSquareCalculator extends RecursiveTask<Integer> {
    
    private final Integer n;
    
    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }
    
    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        log.info("n:" + n);
        
        FactorialSquareCalculator calculator = new FactorialSquareCalculator(n - 1);
        calculator.fork();
        
        return n * n + calculator.join();
    }
}
