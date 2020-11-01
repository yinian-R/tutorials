package com.wymm.concurrent.base;

import java.util.concurrent.BlockingQueue;

public class NumbersConsumer implements Runnable {
    
    private BlockingQueue<Integer> numberQueue;
    private int poisonPill;
    
    public NumbersConsumer(BlockingQueue<Integer> numberQueue, int poisonPill) {
        this.numberQueue = numberQueue;
        this.poisonPill = poisonPill;
    }
    
    @Override
    public void run() {
        try {
            consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    
    private void consume() throws InterruptedException {
        while (true) {
            Integer take = numberQueue.take();
            if (poisonPill == take) {
                return;
            }
            System.out.println(Thread.currentThread().getName() + " result:" + take);
        }
    }
}
