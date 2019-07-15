package com.wymm.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
                System.out.println(Thread.currentThread().getName() + " is released");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
