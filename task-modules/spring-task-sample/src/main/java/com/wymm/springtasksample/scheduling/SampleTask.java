package com.wymm.springtasksample.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class SampleTask {
    
    private final AtomicInteger counts = new AtomicInteger();
    
    @Scheduled(fixedRate = 2000)
    public void execute() {
        log.info("[execute][定时第 ({}) 次执行]", counts.incrementAndGet());
    }
    
}
