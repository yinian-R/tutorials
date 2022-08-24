package com.wymm.stream;

import com.pivovarit.function.ThrowingConsumer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 在 Java 8 中，Lambda 表达式开始通过提供一种表达行为的简洁方式来促进函数式编程。但是，JDK 提供的函数式接口不能很好地处理异常——在处理它们时代码变得冗长而繁琐。
 * 在本文中，我们将探讨一些在编写 lambda 表达式时处理异常的方法。
 *
 * 处理异常方式：
 * 一：使用包装类
 * 二：使用 throwing-function 库（推荐）
 */
public class _9ExceptionTest {
    
    /**
     * 处理未经检查的异常
     */
    @Test
    void processTrow() {
        List<Integer> integers = Arrays.asList(3, 9, 7, 6, 10, 20);
        integers.forEach(i -> System.out.println(50 / i));
        
        // 通过使用传统的try-catch块来解决这个问题
        integers.forEach(i -> {
            try {
                System.out.println(50 / i);
            } catch (ArithmeticException e) {
                System.err.println("Arithmetic Exception occured : " + e.getMessage());
            }
        });
        
        // 为 lambda 函数编写一个 lambda 包装器
        integers.forEach(lambdaWrapper(i -> System.out.println(50 / i)));
        integers.forEach(consumerWrapper(i -> System.out.println(50 / i), ArithmeticException.class));
    
        // 使用 throwing-function 库
        integers.forEach(ThrowingConsumer.unchecked(i -> System.out.println(50 / i)));
}
    
    public static <T> Consumer<T> lambdaWrapper(Consumer<T> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (ArithmeticException e) {
                System.err.println(
                        "Arithmetic Exception occured : " + e.getMessage());
            }
        };
    }
    
    public static <T, E extends Exception> Consumer<T> consumerWrapper(Consumer<T> consumer, Class<E> clazz) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }
    
    
}
