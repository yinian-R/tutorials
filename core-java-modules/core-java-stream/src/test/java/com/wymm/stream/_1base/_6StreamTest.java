package com.wymm.stream._1base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * 并行流
 */
class _6StreamTest {
    private static void debug(int i) {
        System.out.println(i);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void debug2(int i) {
        System.out.println(i + " " + i);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void debug3(int i) {
        System.out.println(Thread.currentThread().getName() + " debug " + i);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void main() {
//        // 调用parallel产生一个并行流
//        long count1 = IntStream.range(1, 100).parallel().peek(_6StreamDemo::debug).count();
//
//        // 调用 parallel/sequential 设置先并行和穿行，最终会以最后一个设置为主
//        long count2 = IntStream.range(1, 100)
//                // 调用parallel产生并行流
//                .parallel().peek(_6StreamDemo::debug)
//                // 调用sequential产生穿行流
//                .sequential().peek(_6StreamDemo::debug2)
//                .count();
//
//        // 并行流使用的线程池是ForkJoinPool.commonPool
//        // 默认线程数是当前机器的CPU个数
//        // 使用这个属性可以修改默认的线程数
//        // 线程名称前缀 ForkJoinPool.commonPool-worker-
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","20");
//        long count3 = IntStream.range(1, 100).parallel().peek(_6StreamDemo::debug3).count();
        
        // 使用自定义的线程池，防止任务被别的任务堵塞
        // 线程名称前缀 ForkJoinPool-1-worker-
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(() -> IntStream.range(1, 100).parallel().peek(_6StreamTest::debug3).count());
        pool.shutdown();
        synchronized (pool) {
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
