package com.wymm.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

class _3FutureTest {
    @Test
    void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> {
            // ...
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run");
            return "hello world";
        });
        
        try {
            // 获取线程返回值
            // String str = future.get();
            // 获取线程返回值。给操作指定超时，如果任务花费时间超过此时间，则抛出 TimeoutException
            String str = future.get(1000L, TimeUnit.MILLISECONDS);
            System.out.println(str);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        
        
    }
}
