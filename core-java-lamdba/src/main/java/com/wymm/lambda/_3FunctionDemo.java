package com.wymm.lambda;

import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * JDK 8 函数接口使用
 */
public class _3FunctionDemo {
    public static void main(String[] args) {
        // 断言函数
        Predicate<Integer> predicate1 = i -> i > 0;
        System.out.println(predicate1.test(-1));
        // Integer类型的断言函数
        IntPredicate predicate2 = i -> i > 0;
        System.out.println(predicate2.test(-1));
        
        // 消费函数接口
        Consumer<String> consumer = s -> System.out.println("输出的数据:" + s);
        consumer.accept("输入的数据");
    }
}
