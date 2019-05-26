package com.wymm.lambda;

@FunctionalInterface
interface interface1{
    int doubleNum(int i);
}

public class _1LambdaDemo {

    public static void main(String[] args) {
        // 最常见的写法
        interface1 interface1 = i -> i*2;
        // 方法里面多行
        interface1 interface2 = i -> {
            return i*2;
        };

        // use
        System.out.println(interface1.doubleNum(3));
        System.out.println(interface2.doubleNum(3));
    }
}
