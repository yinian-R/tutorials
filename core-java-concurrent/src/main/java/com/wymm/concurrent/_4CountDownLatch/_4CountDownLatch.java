package com.wymm.concurrent._4CountDownLatch;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CountDownLatch 有一个计数字段，可以根据需要减少它。然后可以用它来阻塞调用线程，直到计数器为零。
 */
public class _4CountDownLatch {
    
    
    @Test
    public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {
        List<String> outerScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new Worker(outerScraper, countDownLatch))).limit(5).collect(Collectors.toList());
        workers.forEach(Thread::start);
    
        // 调用await保证线程的顺序
        countDownLatch.await();
        outerScraper.add("Latch released");
        outerScraper.forEach(System.out::println);
    }
    
}

class Worker implements Runnable {
    
    private List<String> outerScraper;
    private CountDownLatch countDownLatch;
    
    public Worker(List<String> outerScraper, CountDownLatch countDownLatch) {
        this.outerScraper = outerScraper;
        this.countDownLatch = countDownLatch;
    }
    
    @Override
    public void run() {
        System.out.println("hello world");
        
        outerScraper.add("counted down");
        countDownLatch.countDown();
    }
}
