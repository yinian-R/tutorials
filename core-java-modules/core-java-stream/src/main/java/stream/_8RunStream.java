package stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * 验证 Stream 运行机制
 *
 * 1.所有操作时链式调用，一个元素只迭代一次
 * 2.每一个中间操作返回一个新的流
 * 流里面有一个属性 sourceStage 都指向同一个 Head
 * 3.Head->nextStage(limit)->nextStage(peek)->nextStage(filter)->null
 */
public class _8RunStream {
    public static void main(String[] args) {
        Random random = new Random();
        Stream<Integer> stream = Stream.generate(random::nextInt)
                // 无限流需要短路操作
                .limit(500)
                // 第一个无状态操作
                .peek(s -> System.out.println("peek:" + s))
                // 第二个无状态操作
                .filter(s -> {
                    System.out.println("filter:" + s);
                    return s > 1000000;
                });
        // 执行终止操作
        stream.count();
    }
}
