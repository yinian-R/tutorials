package com.wymm.concurrent.base._3FutureTest;

import com.wymm.concurrent.base.future.FactorialCallableTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Future 获取异步返回结果
 * - get() 阻塞线程，直到线程执行完毕。获取线程返回值
 * - get(long, TimeUnit) 获取线程返回值。给操作指定超时，如果任务花费时间超过此时间，则抛出 TimeoutException
 * - isDone() 告诉我们执行者是否已经完成任务
 * - cancel(boolean) 控制执行任务的线程是否应该被中断
 */
@Slf4j
class _1FutureTest {
    
    @Test
    void usingFuture() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> {
            // ...
            try {
                Thread.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        });
        
        // 阻塞线程，直到线程执行完毕。获取线程返回值
        String str = future.get();
        // 获取线程返回值。给操作指定超时，如果任务花费时间超过此时间，则抛出 TimeoutException
        //String str = future.get(1L, TimeUnit.SECONDS);
        assertEquals(str, "hello world");
    }
    
    /**
     * Callable 简单示例
     */
    @Test
    public void whenException_ThenCallableThrowsIt() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
    
        FactorialCallableTask task = new FactorialCallableTask(5);
        Future<Integer> future = executorService.submit(task);
        Integer result = future.get();
        log.debug(result.toString());
        
        task = new FactorialCallableTask(-5);
        future = executorService.submit(task);
        result = future.get();
        // exception
    }
    
}
