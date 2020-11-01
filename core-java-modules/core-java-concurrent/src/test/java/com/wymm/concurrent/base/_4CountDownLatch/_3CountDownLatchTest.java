package com.wymm.concurrent.base._4CountDownLatch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 使用 CountDownLatch 阻塞线程时，子线程在调用 countDown() 前出了错误，导致它永远不会停止
 * 我们可以设置超时参数避免这种情况出现。当发生异常的时候，我们可以看到 await() 返回 false
 */
class _3CountDownLatchTest {
    
    @Test
    void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        List<Thread> worker = Stream.generate(
                () -> new Thread(new BrokenWorker(countDownLatch, outputScraper))
        ).limit(5).collect(Collectors.toList());
        
        worker.forEach(Thread::start);
        
        //countDownLatch.await();
        boolean isComplete = countDownLatch.await(3L, TimeUnit.SECONDS);
        if (isComplete) {
            // do something
            outputScraper.add("workers complete");
            outputScraper.forEach(System.out::println);
        } else {
            // 子线程有异常
            System.err.println("has error");
        }
        
        
    }
    
    static class BrokenWorker implements Runnable {
        
        private CountDownLatch countDownLatch;
        private List<String> outputScraper;
        
        public BrokenWorker(CountDownLatch countDownLatch, List<String> outputScraper) {
            this.countDownLatch = countDownLatch;
            this.outputScraper = outputScraper;
        }
        
        @Override
        public void run() {
            if (true) {
                throw new RuntimeException("Oh dear,I'm a BrokenWorker");
            }
            countDownLatch.countDown();
            outputScraper.add("count down");
        }
    }
}

