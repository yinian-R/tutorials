package com.wymm.concurrent.base2.stopping;

import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSubThread implements Runnable {
    
    private Thread worker;
    private AtomicBoolean running = new AtomicBoolean(false);
    private int interval;
    
    public ControlSubThread(int interval) {
        this.interval = interval;
    }
    
    public Thread start() {
        worker = new Thread(this);
        worker.start();
        return worker;
    }
    
    public void interrupt(){
        running.set(false);
        worker.interrupt();
    }
    
    public boolean isRunning() {
        return running.get();
    }
    
    public boolean isStopped() {
        return !running.get();
    }
    
    @Override
    public void run() {
        running.set(true);
        while (running.get()){
    
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running.set(false);
                System.out.println("Thread was interrupted, Failed to complete operation");
            }
            // do something
        }
        running.set(false);
    }
}
