package com.wymm.springretrysample.service;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.sql.SQLException;

public interface RetryService {
    
    
    String retry();
    
    
}
