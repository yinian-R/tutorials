# 
## 生命周期
[Java 中线程的生命周期](https://www.baeldung.com/java-thread-lifecycle)

java.lang.Thread 静态枚举 State 定义了线程的潜在状态。在任何给定的时间点，线程只能是以下状态之一：

- NEW - 尚未开始执行的新创建的线程
- RUNNABLE - 运行中或者准备执行，但正在等待资源分配
- BLOCKED - 等待获取监视器锁以进入或者重新进入同步块/方法
- WAITING - 等待其他线程在没有任何时间限制的情况下执行特定操作
- TIME_WAITING - 等待其他线程在指定时间段内执行特定操作
- TERMINATED - 已完成执行

## JDK java.util.concurrent 中最实用的程序

/| desc
---|---
Executor | Executor 是一个表示执行所提供任务的对象的接口
ExecutorService | ExecutorService 是异步处理的完整解决方案
ScheduledExecutorService | 通过使用CountDownLatch，我们可以导致线程阻塞，直到其他线程完成给定的任务
Future | 获取异步返回结果
CountDownLatch | CountDownLatch是一个实用程序类，它将阻塞一组线程，直到完成某些操作为止。<br />CountDownLatch初始化为计数器（整数型）; 随着从属线程完成执行，此计数器递减。但是一旦计数器达到零，其他线程就会被释放
CyclicBarrier | CyclicBarrier 和 CountDownLatch 几乎相同，只是我们可以重用 CyclicBarrier，与 CountDownLatch 不同，它允许多个线程再调用最终任务之前调用 await() 方法（也称为障碍条件）彼此等待。
Semaphore | not finish
ThreadFactory | ThreadFactory 充当线程（不存在）的池，可按需创建线程。它消除了实现高效线程创建机制所需的大量样板代码
BlockingQueue | BlockingQueue 队列，常用于解决并发的生产者-消费者的问题。
DelayQueue | DelayQueue 是无限大小的阻塞队列，只有元素的到期时间（称为用户定义的延迟）完成时才能拉取元素。
Locks | Locks 是比 Synchronized Block 更加灵活，更复杂的线程同步机制
Phaser | Phaser 与 CountDownLatch 非常相似，可以让我们协调线程。Phaser 是动态线程数量需要等待才能继续执行的障碍。使用 Phaser 类实现了具有多个阶段的协调逻辑

### CountDownLatch 和 CyclicBarrier 的区别
CountDownLatch 和 CyclicBarrier 几乎相同，区别在于 CyclicBarrier 可以重复使用

### volatile and Thread Synchronization

对于多线程应用程序，我们需要确保两个一致行为的规则：

- 互斥 - 一次只有一个线程执行关键部分
- 可见性 - 一个线程对共享数据所做的修改对其他线程可见，以保持一致性

**synchronized** 和 **volatile** 以牺牲程序性能为代价提供上述两种属性

**volatile** 是一个非常有用的关键字，因为它可以帮助确保数据更改的可见性，当然，不提供互斥。
因此，在可以多个线程并行执行一个代码的地方，当我们需要确保可见性属性时，它很有用


## Locks
**Locks** 和 **Synchronized Block** 区别：
- 1. **Synchronized Block** 被完全包含在方法中。Locks API 的 `lock()` 和 `unlock()` 可以在单独的方法中操作
- 2. **Synchronized Block** 不支持公平性，锁一旦释放任何线程都可以获取，没有偏好可以指定。通过指定 fairness 属性，可以在 **Lock API** 中实现公平性，确保等待最久的线程获取锁
- 3. 如果一个线程不能访问 **Synchronized Block**，它将被阻塞。**Lock API** 提供的 `tryLock()` 方法，线程仅在可用且未被任何其他线程持有时才获得锁，这减少了线程等待锁的阻塞时间
- 4. 线程处于 waiting 状态无法获取 **Synchronized Block** 的访问，不能中断。**Lock API** 提供了 `lockInterruptibly()`，其可以中断线程

### Lock 接口中的方法：
- `void lock()` - 获取锁（如果有）；如果锁不可用，则线程阻塞，直到释放锁为止
- `void lockInterruptibly()` - 与 Lock() 类似，但是它允许被阻塞的线程中断并通过引发 *java.lang.InterruptedException* 恢复执行
- `boolean tryLock()` - 这是 lock() 的非阻塞版本，它尝试立即获取锁定，如果锁定成功，则返回 true
- `boolean tryLock(long timeout，TimeUnit timeUnit)` - 这个与 tryLock() 类似，除了它放弃尝试获取锁之前的给定的超时
- `void unlock()` – 解锁Lock实例

> Lock 的实例应始终被解锁以避免死锁。建议使用锁的代码块应包含try / catch和finally块

### ReadWriteLock 接口：
除了 Lock 接口，我们还有一个 **ReadWriteLock** 接口，该接口维护一对锁，一个锁用于只读操作，一个锁用于写操作。只要没有写操作，读锁就可以同时由多个线程持有

**ReadWriteLock** 声明获取读或写锁的方法：
- `Lock readLock()` - 返回用于读取的锁
- `Lock writeLock()` - 返回用于写入的锁


## Future
- **ForkJoinTask** 是一个抽象类，该类实现 Future，并且能够由 ForkJoinPool 的少量实际线程托管大量任务。


### fork / join框架
- ForkJoinPool 的每个工作线程都维护着一个工作队列（WorkQueue），这是一个双端队列（Deque），里面存放的对象是任务（ForkJoinTask）。
- 每个工作线程在运行中产生新的任务（通常是因为调用了 fork()）时，会放入工作队列的队头，并且工作线程在处理自己的工作队列时，使用的是 LIFO 方式，也就是说每次从队头取出任务来执行
- 为了尽可能的提高CPU的利用率，空闲的线程将从其他线程的队列中窃取任务来执行，从workQueue的队尾窃取任务，从而减少竞争，任务的窃取是遵从FIFO顺序进行的，因为先放入的任务往往表示更大的工作量，窃取来的任务支持进一步的递归分解。
- 在遇到 join() 时，如果需要 join 的任务尚未完成，则会先处理其他任务，并等待其完成
- 在既没有自己的任务，也没有可以窃取的任务时，进入休眠

使用fork / join框架可以加快大型任务的处理速度，但是要实现此结果，应遵循一些准则：
- **使用尽可能少的线程池** –在大多数情况下，最佳决定是每个应用程序或系统使用一个线程池
- 如果不需要特定的调整，**请使用默认的公共线程池**
- **使用合理的阈值**将ForkJoinTask拆分为子任务
- **避免ForkJoinTasks中的任何阻塞**






## 参考
[java-volatile](https://www.baeldung.com/java-volatile)
