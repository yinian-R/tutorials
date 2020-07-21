package com.wymm.concurrent.base2;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SynchronizedMethods {
    public static int staticSum = 0;
    private int sum = 0;
    
    public void calculate() {
        setSum(getSum() + 1);
    }
    
    public synchronized void synchronizedCalculate() {
        setSum(getSum() + 1);
    }
    
    public static synchronized void synchronizedStaticCalculate() {
        staticSum += 1;
    }
    
    
    public void performSynchronizedTask() {
        synchronized (this) {
            setSum(getSum() + 1);
        }
    }
    
    public static void performStaticSynchronizedTask() {
        synchronized (SynchronizedMethods.class) {
            staticSum += 1;
        }
    }
}
