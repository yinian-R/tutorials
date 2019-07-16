#### JDK java.util.concurrent 中最实用的程序


/| desc
---|---
Executor | Executor是一个表示执行提供的任务的对象的接口
ExecutorService | ExecutorService是异步处理的完整解决方案
ScheduledExecutorService | 通过使用CountDownLatch，我们可以导致线程阻塞，直到其他线程完成给定的任务
Future | row 2 col 2
CountDownLatch | row 1 col 2
CyclicBarrier | row 2 col 2
Semaphore | row 2 col 2
ThreadFactory | row 1 col 2
BlockingQueue | row 2 col 2
DelayQueue | row 1 col 2
Locks | row 2 col 2
Phaser | row 2 col 2


#### CountDownLatch 和 CyclicBarrier 的区别
CountDownLatch 和 CyclicBarrier 几乎相同，区别在于 CyclicBarrier 可以重复使用