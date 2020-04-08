package com.wymm.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 和 CountDownLatch 几乎相同，只是我们可以重用 CyclicBarrier 
 * 与 CountDownLatch 不同，它允许多个线程再调用最终任务之前调用 await() 方法（也称为障碍条件）彼此等待。
 */
public class _5CyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("All task are completed");
        });

        Thread thread1 = new Thread(new Task(cyclicBarrier), "T1");
        Thread thread2 = new Thread(new Task(cyclicBarrier), "T2");
        Thread thread3 = new Thread(new Task(cyclicBarrier), "T3");

        if (!cyclicBarrier.isBroken()) {
            System.out.println("not broken");
            thread1.start();
            thread2.start();
            thread3.start();
        }

        System.out.println(Thread.currentThread().getName() + " are completed");
    }

    static class Task implements Runnable {

        private CyclicBarrier cyclicBarrier;

        public Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting");
                cyclicBarrier.await();
                // 最终任务
                System.out.println(Thread.currentThread().getName() + " is released");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
