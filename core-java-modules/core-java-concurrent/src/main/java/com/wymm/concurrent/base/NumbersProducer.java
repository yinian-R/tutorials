package com.wymm.concurrent.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class NumbersProducer implements Runnable {
    
    private BlockingQueue<Integer> numberQueue;
    private int poisonPill;
    private int poisonPillPerProducer;
    
    public NumbersProducer(BlockingQueue<Integer> numberQueue, int poisonPill, int poisonPillPerProducer) {
        this.numberQueue = numberQueue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
    }
    
    @Override
    public void run() {
        try {
            generateNumbers();
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    
    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            numberQueue.put(ThreadLocalRandom.current().nextInt(100));
        }
        
        for (int i = 0; i < poisonPillPerProducer; i++) {
            numberQueue.put(poisonPill);
        }
    }
}
