package com.wymm.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Phaser 与 CountDownLatch 非常相似，可以让我们协调线程。Phaser 是动态线程数量需要等待才能继续执行的障碍。使用 Phaser 类实现了具有多个阶段的协调逻辑
 * 在 CountDownLatch 中，该数字不能动态配置，只能在创建实例时提供。
 * - register() : 注册一个需要协作的线程
 * - arriveAndAwaitAdvance() ： 到达屏障后，等待其他线程
 * - arriveAndDeregister() ： 到达屏障后，注销自己，无需等待其他线程
 * - getPhase() : 返回当前阶段数
 * <p>
 * 从主线程创建 Phaser 实例时，传递1作为参数。这等效于从当前线程调用 register() 方法
 */
class _11PhaserTest {
    
    /**
     * 假设我们要协调行动的多个阶段。三个线程处理第一个阶段，两个线程处理第二个阶段
     */
    @Test
    void usingPhaser() throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser phaser = new Phaser(1) {
            // 参与协作的线程都到达屏障后，会调用该方法。phase 是当前阶段号，在升级之前的
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                switch (phase) {
                    case 0:
                        System.out.println("第一阶段：三个线程+协调线程完成");
                        break;
                    case 1:
                        System.out.println("第二阶段：两个线程+协调线程完成");
                        break;
                }
                return super.onAdvance(phase, registeredParties);
            }
        };
        assertEquals(0, phaser.getPhase());
        
        // when
        executorService.submit(new LongRunningAction("thread-1", phaser));
        executorService.submit(new LongRunningAction("thread-2", phaser));
        executorService.submit(new LongRunningAction("thread-3", phaser));
        
        // then
        phaser.arriveAndAwaitAdvance();
        assertEquals(1, phaser.getPhase());
        
        // and
        executorService.submit(new LongRunningAction("thread-4", phaser));
        executorService.submit(new LongRunningAction("thread-5", phaser));
        phaser.arriveAndAwaitAdvance();
        assertEquals(2, phaser.getPhase());
        
        phaser.arriveAndDeregister();
    }
    
    /**
     * 假设我们要协调行动的多个阶段。三个线程处理各自游戏的阶段，必须每个游戏阶段都完成才进行下一阶段
     */
    @Test
    void usingPhaser_thenInnerPhaser() throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser phaser = new Phaser(0) {
            // 参与协作的线程都到达屏障后，会调用该方法。phase 是当前阶段号，在升级之前的
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                switch (phase) {
                    case 0:
                        System.out.println("预赛完成");
                        break;
                    case 1:
                        System.out.println("初赛完成");
                        break;
                    case 2:
                        System.out.println("决赛完成");
                        break;
                }
                return super.onAdvance(phase, registeredParties);
            }
        };
        assertEquals(0, phaser.getPhase());
        
        IntStream.range(0, 5)
                .mapToObj(i -> new GameAction("thread-" + i, phaser))
                .forEach(executorService::submit);
        
        TimeUnit.MILLISECONDS.sleep(100);
    }
    
}
