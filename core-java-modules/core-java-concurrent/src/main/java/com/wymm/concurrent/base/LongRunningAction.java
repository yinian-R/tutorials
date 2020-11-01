package com.wymm.concurrent.base;

import java.util.concurrent.Phaser;

public class LongRunningAction implements Runnable {
    
    private String threadName;
    private Phaser phaser;
    
    public LongRunningAction(String threadName, Phaser phaser) {
        this.threadName = threadName;
        this.phaser = phaser;
        phaser.register();
    }
    
    @Override
    public void run() {
        System.out.println(threadName + ": This is phase " + phaser.getPhase());
        // ...
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
    }
}
