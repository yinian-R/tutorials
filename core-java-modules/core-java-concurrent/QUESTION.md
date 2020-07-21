> 程序有多个线程共享一个A变量，写线程对A变量进行修改，读线程读取A变量，
许多人可能希望短暂的延迟后读取到写入值。但是实际上，延迟可能变的更长，它甚至可能永久的挂起，
是什么原因？

这些异常的原因是缺乏内存可见性和重新排序。

```
public class TaskRunner {
 
    private static int number;
    private static boolean ready;
 
    private static class Reader extends Thread {
 
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
 
            System.out.println(number);
        }
    }
 
    public static void main(String[] args) {
        new Reader().start();
        number = 42;
        ready = true;
    }
}
```
**可见性**

让我们设想一个场景，在这个场景中，操作系统在两个不同的内核中调度线程，其中：

- 主线程在 Core Cache 具有 number 变量的副本
- 读线程在 Core Cache 也有 number 变量的副本
- 主线程更新 Cache 中 number 的值
- 读线程读取 number 变量

在大多数现代处理器上，写请求在发出去后不会立马被应用。实际上，处理器倾向于将这些写操作排队到一个特殊的写缓存区中。
一段时间后，它们会将这些写入一次性全部应用到主存储器。

综上所述，当主线程更新 number 变量后，无法保证读线程看到什么。换句话说，读线程可能会立马看到更新的值，或者有些延迟，或者根本看不到

这就是内存可见性可能引起异常的原因。

**重新排序**

更糟糕的是，读取线程可会以实际程序顺序之外的任何顺序查看这些写入

重新排序是一种性能改进的优化技术。有趣的是，不同的组件可以应用此优化技术：

- 处理器可以按程序顺序以外的任何顺序刷新其写缓存
- 处理器可能会应用乱序执行技术
- JIT 编译器可以通过重新排序进行优化

**volatile**

为了确保变量的更新可预测地传播到其他线程，我们应该使用 volatile 修饰变量
```
public class TaskRunner {
    private volatile static int number;
}
```

这样，在运行时和处理器通信，不会对 volatile 变量的任何指令进行重新排序。
此外，处理器知道它们应该立即刷新这些变量的任何更新。