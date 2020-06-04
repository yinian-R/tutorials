package com.wymm.concurrent.base2;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 在多线程环境中，当两个或多个线程试图同时更新可变共享数据时，就会发生竞争状态。Java通过同步对共享数据的线程访问提供了一种避免竞争条件的机制。
 * synchronized 标记代码为同步块，只允许一个线程在任何给定时间运行
 * synchronized 可以被实例方法、静态方法、代码块使用
 */
class SynchronizedTestTest {
    
    /**
     * 多线程执行几乎每次都会失败
     */
    @Test
    public void givenMultiThread_whenNonSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods method = new SynchronizedMethods();
        
        IntStream.range(0, 1000).forEach(i -> service.submit(method::calculate));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        
        assertEquals(1000, method.getSum());
    }
    
    /**
     * 使用 synchronized 的方法，结果就正确了
     * @throws InterruptedException
     */
    @Test
    public void givenMultiThread_whenSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods method = new SynchronizedMethods();
        
        IntStream.range(0, 1000).forEach(i -> service.submit(method::synchronizedCalculate));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
    
        assertEquals(1000, method.getSum());
    }
    
    /**
     * 被 synchronized 修饰的方法与该类的Class对象同步，并且每个类仅在 JVM 存在一个 Class 对象
     * 因此每个类在一个静态同步方法内只能执行一个线程，而不管它有多少个实例
     */
    @Test
    public void givenMultiThread_whenStaticSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        
        IntStream.range(0, 1000).forEach(i -> service.submit(SynchronizedMethods::synchronizedStaticCalculate));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        
        assertEquals(1000, SynchronizedMethods.staticSum);
    }
    
    /**
     * 有时我们不想同步整个方法，而只同步其中的一部分代码。这时可以通过 synchronized 作用于代码块
     * 我们将 this 作为参数传递给 synchronized。这时监视对象上的同步。简而言之，每个监视对象只有一个线程可以执行该代码块里面的代码
     */
    @Test
    public void givenMultiThread_whenSyncBlock() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods method = new SynchronizedMethods();
        
        IntStream.range(0, 1000).forEach(i -> service.submit(method::performSynchronizedTask));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        
        assertEquals(1000, method.getSum());
    }
    
    /**
     * 如果是静态方法，我们可以使用类名代替对象引用作为参数传递给 synchronized。该类将成为该块代码的同步监视器
     */
    @Test
    public void givenMultiThread_whenStaticSyncBlock() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        
        IntStream.range(0, 1000).forEach(i -> service.submit(SynchronizedMethods::performStaticSynchronizedTask));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        
        assertEquals(1000, SynchronizedMethods.staticSum);
    }
}