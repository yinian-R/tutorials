#### JDK java.util.concurrent 中最实用的程序


/| desc
---|---
Executor | Executor是一个表示执行提供的任务的对象的接口
ExecutorService | ExecutorService是异步处理的完整解决方案
ScheduledExecutorService | 通过使用CountDownLatch，我们可以导致线程阻塞，直到其他线程完成给定的任务
Future | 获取异步返回结果
CountDownLatch | CountDownLatch是一个实用程序类，它将阻塞一组线程，直到完成某些操作为止。<br />CountDownLatch初始化为计数器（整数型）; 随着从属线程完成执行，此计数器递减。但是一旦计数器达到零，其他线程就会被释放
CyclicBarrier | CyclicBarrier 和 CountDownLatch 几乎相同，只是我们可以重用 CyclicBarrier，与 CountDownLatch 不同，它允许多个线程再调用最终任务之前调用 await() 方法（也称为障碍条件）彼此等待。
Semaphore | row 2 col 2
ThreadFactory | row 1 col 2
BlockingQueue | row 2 col 2
DelayQueue | row 1 col 2
Locks | row 2 col 2
Phaser | row 2 col 2


#### CountDownLatch 和 CyclicBarrier 的区别
CountDownLatch 和 CyclicBarrier 几乎相同，区别在于 CyclicBarrier 可以重复使用