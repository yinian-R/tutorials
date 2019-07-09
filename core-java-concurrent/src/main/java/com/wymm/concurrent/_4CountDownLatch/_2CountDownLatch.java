package com.wymm.concurrent._4CountDownLatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class _2CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completeThreadCounter = new CountDownLatch(5);
        
        List<Thread> workers = Stream.generate(() -> new Thread(
                new WaitingWorker(outputScraper, readThreadCounter, callingThreadBlocker, completeThreadCounter)
        )).limit(5).collect(Collectors.toList());
        
        workers.forEach(Thread::start);
        readThreadCounter.await();
        outputScraper.add("worker ready");
        callingThreadBlocker.countDown();
        completeThreadCounter.await();
        outputScraper.add("worker complete");
        
        
        outputScraper.forEach(System.out::println);
    }
}


class WaitingWorker implements Runnable {
    
    private List<String> outputScraper;
    private CountDownLatch readThreadCounter;
    private CountDownLatch callingThreadBlocker;
    private CountDownLatch completeThreadCounter;
    
    public WaitingWorker(List<String> outputScraper, CountDownLatch readThreadCounter, CountDownLatch callingThreadBlocker, CountDownLatch completeThreadCounter) {
        this.outputScraper = outputScraper;
        this.readThreadCounter = readThreadCounter;
        this.callingThreadBlocker = callingThreadBlocker;
        this.completeThreadCounter = completeThreadCounter;
    }
    
    @Override
    public void run() {
        readThreadCounter.countDown();
        
        try {
            callingThreadBlocker.await();
            // do something
            outputScraper.add("counted down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            completeThreadCounter.countDown();
        }
    }
}