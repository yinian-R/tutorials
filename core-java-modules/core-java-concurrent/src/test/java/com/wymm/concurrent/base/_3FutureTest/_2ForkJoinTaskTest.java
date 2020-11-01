package com.wymm.concurrent.base._3FutureTest;

import com.wymm.concurrent.base.future.CustomRecursiveAction;
import com.wymm.concurrent.base.future.CustomRecursiveTask;
import com.wymm.concurrent.base.future.FactorialSquareCalculator;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * fork/join框架是Java 7提出的，它提供了一些工具，可通过尝试使用所有可用的处理器内核来帮助加速并行处理 - 这是通过分而治之的方法来实现的
 * 这意味着框架首先的 fork，将任务递归地分解为较小的独立小任务，知道它足够简单以至于可异步执行
 * 此后 join，其中所有子任务的结构都递归地合并为单个结果，或者在 void 的情况下，程序等待直到每个子任务完成
 * <p>
 * 为了提供有效的并行执行，fork/join框架使用称为ForkJoinPool的线程池，该线程池管理 ForkJoinWorkerThread 类型的工作线程
 * <p>
 * <p>
 * <p>
 * ForkJoinTask是在ForkJoinPool内部执行的任务的基本类型。
 * 在实践中，它的两个子类之一应该扩展：在RecursiveAction为空隙任务和RecursiveTask <V>该返回值的任务。
 * 它们都有一个抽象方法compute（），其中定义了任务的逻辑
 * ForkJoinTask的主要特征是它通常会生成新的子任务，作为完成其主要任务所需工作的一部分。
 * - fork（）生成新任务
 * - join（）收集所有结果
 */
class _2ForkJoinTaskTest {
    
    /**
     * 使用 RecursiveTask 来实现递归
     * 通过调用阻塞的方法 fork()，我们要求 ForkJoinPool 初始化子任务的执行
     */
    @Test
    void usingForkJoinTask() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        
        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);
        forkJoinPool.execute(calculator);
        Integer result = calculator.get();
        assertEquals(result, 385);
    }
    
    /**
     * 使用 RecursiveAction 处理 void tasks
     */
    @Test
    void usingRecursiveAction() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String workload = "qwqertyuiop[asdfghjkl;zxcvbnm,./qwqertyuiop[asdfghjkl;zxcvbnm,./qwqertyuiop[asdfghjkl;zxcvbnm,./qwqertyuiop[asdfghjkl;zxcvbnm,./";
        CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction(workload);
        forkJoinPool.execute(customRecursiveAction);
        
        Thread.sleep(100);
    }
    
    /**
     * 使用 RecursiveTask 处理 <V> tasks
     */
    @Test
    void usingRecursiveTask() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 5, 6, 7, 8, 9, 10};
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask(arr);
        forkJoinPool.execute(customRecursiveTask);
        
        Integer result = customRecursiveTask.join();
        
        assertEquals(result, 50);
    }
}
