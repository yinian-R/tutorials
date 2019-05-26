package com.wymm.lambda;

import java.util.function.Predicate;

public class _3FunctionDemo {
    public static void main(String[] args) {
        // 断言函数
        Predicate<Integer> predicate = i->i>0;
        System.out.println(predicate.test(-1));
    }
}
