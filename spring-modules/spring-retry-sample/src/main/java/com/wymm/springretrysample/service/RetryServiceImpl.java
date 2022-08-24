package com.wymm.springretrysample.service;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryServiceImpl implements RetryService {
    
    public static int temp = 0;
    
    @Retryable(value = AuthException.class, maxAttempts = 2)
    @Override
    public String retry() {
        System.out.println("run retry()");
        if (temp < 3) {
            System.out.println("error " + temp);
            temp += 1;
            throw new AuthException();
        }
        
        return "";
    }
}
