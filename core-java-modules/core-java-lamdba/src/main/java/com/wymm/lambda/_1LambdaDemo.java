package com.wymm.lambda;

/**
 * lambda 表达式常见使用写法
 */
public class _1LambdaDemo {

    public static void main(String[] args) {
        // 最常见的写法
        Interface1 interface1 = i -> i*2;
        // 方法里面多行
        Interface1 interface2 = i -> {
            return i*2;
        };

        // use
        System.out.println(interface1.doubleNum(3));
        System.out.println(interface2.doubleNum(3));
    }

    @FunctionalInterface
    interface Interface1 {
        int doubleNum(int i);
    }
}

