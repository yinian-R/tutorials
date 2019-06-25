package com.wymm.concurrent;

import java.util.concurrent.*;

public class _2ExecutorService {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 创建线程后，提交任务
        executorService.submit(new Task());
        // 提交任务时创建 Runnable
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " hello world");
        });
        
        
        // 创建单线程
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.submit(new Task());
        
        
        // 创建定期执行线程
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Future<String> schedule = scheduledExecutorService.schedule(() -> {
            // ...
            System.out.println("hello world schedule 1");
            return "hello world 1";
        }, 1, TimeUnit.SECONDS);
        
        // 获取线程返回值
        System.out.println(schedule.get());
        
        // 不再接受新的任务，执行完任务关闭executor
        //scheduledExecutorService.shutdown();
        // 不再接受新的任务，立刻终止所有未执行的任务，关闭executor
        //scheduledExecutorService.shutdownNow();
        
        // 创建一个周期性操作。该操作再指定延迟后开始运行，随后在给定的周期到了执行，直到服务终止。
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("scheduleAtFixedRate");
        }, 1, 1, TimeUnit.SECONDS);
    
        // 创建一个周期性操作。该操作再指定延迟后开始运行，任务执行完毕随后在给定的周期反复执行，直到服务终止。
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("scheduleWithFixedDelay");
        }, 1, 2, TimeUnit.SECONDS);
        
    }
    
}


class Task implements Runnable {
    
    @Override
    public void run() {
        // task details
        System.out.println(Thread.currentThread().getName() + " hello world");
    }
}