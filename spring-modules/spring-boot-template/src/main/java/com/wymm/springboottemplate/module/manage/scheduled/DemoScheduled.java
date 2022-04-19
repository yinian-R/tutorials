package com.wymm.springboottemplate.module.manage.scheduled;

import com.wymm.springboottemplate.config.scheduled.DemoScheduledConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DemoScheduled {
    
    private static final ReentrantLock LOCK = new ReentrantLock();
    
    @Scheduled(cron = DemoScheduledConfig.CRON)
    private void process() throws InterruptedException {
        if (!LOCK.tryLock(1, TimeUnit.SECONDS)) {
            log.warn("LOCK fail, over");
            return;
        }
        try {
            log.debug("定时任务下发，开始");
            // do something
            
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.debug("定时任务下发，结束");
            LOCK.unlock();
        }
    }
    
}
