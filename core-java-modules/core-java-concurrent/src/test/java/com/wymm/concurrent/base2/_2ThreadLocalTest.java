package com.wymm.concurrent.base2;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 能够为当前线程存储数据，并将其包装在特殊类型的对象中。
 * <p>
 * 每个线程都持有一个 ThreadLocalMap，键是当前线程，使用时若已实例化则直接使用已存在的对象
 * 因此，从池中获取线程，若线程对应的 ThreadLocalMap 已经存在，则新线程可能获取到相同的 ThreadLocal 数据，这可能导致令人惊讶的结果
 * 解决此问题的一种方法是在使用完每个 ThreadLocal 后手动删除。
 */
@Slf4j
class _2ThreadLocalTest {
    
    /**
     * 使用 ThreadLocal 和线程池
     */
    @Test
    void usingThreadLocalAndThreadPoolExecutor() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        for (int i = 0; i < 5; i++) {
            ThreadLocalWithUserContext context = new ThreadLocalWithUserContext(i);
            executorService.submit(context);
        }
        
        executorService.shutdown();
    }
    
}
