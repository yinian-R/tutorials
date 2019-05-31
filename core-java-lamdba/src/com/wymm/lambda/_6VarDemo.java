package com.wymm.lambda;

import java.util.function.Consumer;

/**
 * 变量引用
 */
public class _6VarDemo {
    public static void main(String[] args) {
        String str = "我们的时间：";
        //str = ""; // 定义之后修改会报错，因为方法引用是匿名内部类，所调用的外部对象都是final修饰才可以，或者实际上是final，实际上是final是指定义后就不改了
        Consumer<String> consumer = s -> System.out.println(str + s);
        consumer.accept("12点");
    }
}
