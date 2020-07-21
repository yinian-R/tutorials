### 概念

### JDK java.util.concurrent 中最实用的程序

/| desc
---|---
Executor | Executor是一个表示执行提供的任务的对象的接口
ExecutorService | ExecutorService是异步处理的完整解决方案
ScheduledExecutorService | 通过使用CountDownLatch，我们可以导致线程阻塞，直到其他线程完成给定的任务
Future | 获取异步返回结果
CountDownLatch | CountDownLatch是一个实用程序类，它将阻塞一组线程，直到完成某些操作为止。<br />CountDownLatch初始化为计数器（整数型）; 随着从属线程完成执行，此计数器递减。但是一旦计数器达到零，其他线程就会被释放
CyclicBarrier | CyclicBarrier 和 CountDownLatch 几乎相同，只是我们可以重用 CyclicBarrier，与 CountDownLatch 不同，它允许多个线程再调用最终任务之前调用 await() 方法（也称为障碍条件）彼此等待。
Semaphore | not finish
ThreadFactory | ThreadFactory 充当线程（不存在）的池，可按需创建线程。它消除了实现高效线程创建机制所需的大量样板代码
BlockingQueue | BlockingQueue 队列，常用于解决并发的生产者-消费者的问题。
DelayQueue | DelayQueue 是无限大小的阻塞队列，只有元素的到期时间（称为用户定义的延迟）完成时才能拉取元素。
Locks | row 2 col 2
Phaser | row 2 col 2

### CountDownLatch 和 CyclicBarrier 的区别
CountDownLatch 和 CyclicBarrier 几乎相同，区别在于 CyclicBarrier 可以重复使用

### volatile and Thread Synchronization

对于多线程应用程序，我们需要确保两个一致行为的规则：

- 互斥 - 一次只有一个线程执行关键部分
- 可见性 - 一个线程对共享数据所做的修改对其他线程可见，以保持一致性

synchronized 和 volatile 以牺牲程序性能为代价提供上述两种属性

volatile 是一个非常有用的关键字，因为它可以帮助确保数据更改的可见性，当然，不提供互斥。
因此，在可以多个线程并行执行一个代码的地方，它很有用，但我们需要确保可见性属性

## 参考
[java-volatile](https://www.baeldung.com/java-volatile)
