package com.wymm.springretrysample.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RetryServiceImplTest {
    
    @Autowired
    private RetryService retryService;
    
    @Test
    void retry() {
        retryService.retry();
    }
    
}