package com.wymm.concurrent.base._10LocksTest;

import org.junit.jupiter.api.Test;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock类实现了Lock接口
 * <p>
 * 重入锁的搭档：Condition。线程之间的交互，主要通过两个函数完成，Object.wait() 和 Object.notify()，通过 Lock 接口的 newCondition() 生成 Condition 的实例，
 * 利用 Condition 对象可以让线程在合适的时间等待，或者通知继续执行
 * - await()方法会使线程等待，同时释放当前锁，当前线程加入Condition对象维护的等待队列中，当其他线程中使用signal()或signalAll()方法时，线程会重新获得锁继续执行。或当线程被中断时，也会跳出等待。
 * - awaitUninterruptibly()方法与await()方法基本相同，但它不会在等待过程中响应中断。
 * - signal()方法用于唤醒一个在等待中的线程。signalAll()方法会唤醒所有在等待的线程
 */
class _1ReentrantLockTest {
    
    private final ReentrantLock lock = new ReentrantLock();
    
    Stack<String> stack = new Stack<>();
    int CAPACITY = 5;
    
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();
    
    /**
     * 使用 ReenrtantLock 做同步
     */
    @Test
    void perform() {
        lock.lock();
        try {
            // do something
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * 调用 tryLock() 的线程至多等待一秒钟，如果锁不可用，则将放弃等待
     */
    @Test
    void performTryLock() throws InterruptedException {
        boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
        if (isLockAcquired) {
            try {
                // do something
            } finally {
                lock.unlock();
            }
        }
    }
    
    /**
     * 使用 Lock 的 Condition 进行线程交互
     */
    @Test
    void usingCondition() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        _1ReentrantLockTest reentrantLockTest = new _1ReentrantLockTest();
        
        executorService.submit(() -> {
            System.out.println("pop:" + reentrantLockTest.popFromStack());
        });
        executorService.submit(() -> {
            System.out.println("pop:" + reentrantLockTest.popFromStack());
        });
        
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            executorService.submit(() -> {
                reentrantLockTest.pushToStack(String.valueOf(finalI));
                System.out.println("push:" + finalI);
            });
        }
        
        TimeUnit.SECONDS.sleep(1);
    }
    
    public void pushToStack(String item) {
        try {
            lock.lock();
            while (stack.size() == CAPACITY) {
                stackFullCondition.await();
            }
            stack.push(item);
            stackEmptyCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public String popFromStack() {
        try {
            lock.lock();
            while (stack.size() == 0) {
                stackEmptyCondition.await();
            }
            return stack.pop();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            stackFullCondition.signalAll();
            lock.unlock();
        }
    }
}
