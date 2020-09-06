package com.wymm.concurrent.base;

import lombok.SneakyThrows;

import java.util.concurrent.Phaser;

public class GameAction implements  Runnable{
    
    private String threadName;
    private Phaser phaser;
    
    public GameAction(String threadName, Phaser phaser) {
        this.threadName = threadName;
        this.phaser = phaser;
        phaser.register();
    }
    
    @SneakyThrows
    @Override
    public void run() {
        // ...
        System.out.println(threadName+"：预赛");
        phaser.arriveAndAwaitAdvance();
    
        // ...
        System.out.println(threadName+"：初赛");
        phaser.arriveAndAwaitAdvance();
    
        // ...
        System.out.println(threadName+"：决赛");
        phaser.arriveAndAwaitAdvance();
        
        phaser.arriveAndDeregister();
    }
}
