package com.wymm.concurrent.base2;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ThreadPoolExecutor 是一个可扩展的线程池实现。有许多参数和钩子用于微调
 * <p>
 * 主要参数：corePoolSize，maximumPoolSize 和 keepAliveTime
 * 池由固定数量的核心线程组成，这些线程一直在里面，还有一些多余的线程，这些线程可能在不需要它们的时候终止。corePoolSize 参数是将被实例化，
 * 并保存在池中的核心线程数。当一个新任务进来时，所有核心线程都忙并且内部队列已满，则允许把池中的线程数增长到 maximumPoolSize。
 * keepAliveTime 参数是允许过多线程(实例化超过 corePoolSize)以空闲状态存在的时间间隔。默认情况下，ThreadPoolExecutor只考虑要删除的非核心线程。
 * 为了对核心线程应用相同的删除策略，我们可以使用 allowCoreThreadTimeout(true) 方法
 * <p>
 * 这些参数涵盖了广泛的用例，但是最典型的配置是在{@link Executors}静态方法中预定义的。
 */
@Slf4j
class _4ThreadPoolExecutor {
    
    /**
     * 实例化了一个具有固定线程数2的ThreadPoolExecutor。
     * 这意味着，如果同时运行的任务数量始终小于或等于两个，则它们将立即执行。否则，其中一些任务可能会排队等待轮到他们。
     */
    @Test
    void usingNewFixedThreadPool() {
        // newFixedThreadPool方法创建一个corePoolSize和maximumPoolSize参数值相等且keepAliveTime为零的ThreadPoolExecutor 。这意味着该线程池中的线程数始终相同
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        
        assertEquals(2, executor.getPoolSize());
        assertEquals(1, executor.getQueue().size());
    }
    
    /**
     * newCachedThreadPool()方法创建另一个预配置的ThreadPoolExecutor。
     * 此方法根本不接收多个线程。此实例的corePoolSize实际上设置为0，maximumPoolSize设置为Integer.MAX_VALUE。该时间的keepAliveTime为60秒。
     * 这些参数值表示该线程池可能会无限增长，以容纳任意数量的已提交任务。但是，当不再需要线程时，将在60秒钟不活动之后将其丢弃。
     * 一个典型的用例是您的应用程序中有很多短期任务。
     */
    @Test
    void usingNewCachedThreadPool() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        
        assertEquals(3, executor.getPoolSize());
        // 上面的示例中的队列大小将始终为零，因为内部使用了 SynchronousQueue 实例。
        // 在 SynchronousQueue 中，成对的插入和删除操作始终同时发生，因此队列实际上从不包含任何内容。
        assertEquals(0, executor.getQueue().size());
    }
    
    /**
     * newSingleThreadExecutor() API创建的另一典型形式的ThreadPoolExecutor含有单个线程。
     * 单线程执行程序是创建事件循环的理想选择。corePoolSize和maximumPoolSize参数等于1，并且KeepAliveTime的是零。
     * <p>
     * 另外，这个ThreadPoolExecutor是用一个不可变的包装器修饰的，因此在创建之后不能重新配置它。请注意，这也是我们不能将其强制转换为ThreadPoolExecutor的原因。
     */
    @Test
    void usingNewSingleThreadExecutor() {
        AtomicInteger counter = new AtomicInteger();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            counter.set(1);
        });
        executor.submit(() -> {
            counter.compareAndSet(1, 2);
        });
    }
    
    
    /**
     * - schedule 方法允许在指定的延迟后执行一次任务；
     * - scheduleAtFixedRate 方法创建一个固定速度的计划。该操作再指定初始延迟后开始运行，随后在给定的周期到了执行，直到服务终止。（该周期参数是时间的任务的开始时间之间测量的，所以执行速率是固定的）
     * - scheduleWithFixedDelay 方法创建一个具有固定延迟的计划。该操作再指定初始延迟后开始运行，任务执行完毕随后在给定的延迟反复执行，直到服务终止。
     */
    @Test
    void usingNewScheduledThreadPool() throws InterruptedException {
        log.info("start");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.schedule(() -> {
            log.info("schedule: Hello World");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 50, TimeUnit.MILLISECONDS);
    
    
        CountDownLatch lock = new CountDownLatch(3);
        //executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            log.info("FixedRate:Hello World");
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);
    
        lock.await(2000, TimeUnit.MILLISECONDS);
        future.cancel(true);
    }
}
