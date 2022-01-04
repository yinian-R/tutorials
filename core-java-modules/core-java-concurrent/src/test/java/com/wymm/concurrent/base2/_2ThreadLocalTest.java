package com.wymm.concurrent.base2;

import com.wymm.concurrent.base2.threadlocal.ThreadLocalAwareThreadPool;
import com.wymm.concurrent.base2.threadlocal.ThreadLocalWithUserContext;
import com.wymm.concurrent.base2.threadlocal.ThreadLocalWithUserContext2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * ThreadLocal 能够为当前线程存储数据，并将其包装在特殊类型的对象中。
 * <p>
 * 每个线程都持有一个 ThreadLocalMap，键是当前线程，使用时若已实例化则直接使用已存在的对象
 * 因此，从池中获取线程，若线程对应的 ThreadLocalMap 已经存在，则新线程可能获取到相同的 ThreadLocal 数据，这可能导致令人惊讶的结果
 * 解决此问题的一种方法是在使用完每个 ThreadLocal 后手动删除。
 */
@Slf4j
class _2ThreadLocalTest {
    
    /**
     * 使用 ThreadLocal 和线程池
     *
     * 当我们一起使用ThreadLocal和线程池时，我们应该格外小心，注意清除数据
     * 为了更好地理解可能的警告，让我们考虑下列情形：
     * 1.首先，应用程序从池中借用一个线程
     * 2.使用ThreadLocal存储限制的数据
     * 3.当前执行完毕，应用程序将借用的线程返回到池中
     * 4.一段时间后，应用程序借用同一个线程来处理另一个请求
     * 5.由于应用程序上次执行未执行必要的清除，因此它可能会为新请求重新使用相同的ThreadLoad数据
     */
    @Test
    void usingThreadLocalAndThreadPoolExecutor() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        for (int i = 0; i < 5; i++) {
            ThreadLocalWithUserContext context = new ThreadLocalWithUserContext(i);
            executorService.submit(context);
        }
        
        executorService.shutdown();
    }
    
    /**
     * 扩展 ThreadPoolExecutor 使用 afterExecute() 钩子清除 ThreadLocal 数据
     */
    @Test
    void usingThreadLocalAndThreadPoolExecutor_afterExecuteClear() throws InterruptedException {
        ExecutorService executorService = new ThreadLocalAwareThreadPool(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        
        for (int i = 0; i < 5; i++) {
            ThreadLocalWithUserContext2 context = new ThreadLocalWithUserContext2(i);
            executorService.submit(context);
        }
        
        executorService.shutdown();
    }
    
}
