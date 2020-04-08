package com.wymm.lambda;

import java.util.function.Function;

/**
 * 级联表达式和柯里化
 * 柯里化：把多个参数的函数变成一个参数的函数，并且返回接受余下参数且返回结果的函数
 * 柯里化的目的：函数标准化。所有的函数都是只有一个参数，调用起来比较灵活，可以把一些函数组合起来
 * 高阶函数：返回函数的函数就是高阶函数
 */
public class _7CurryDemo {
    public static void main(String[] args) {
        // 实现一个x+y的级联表达式
        Function<Integer, Function<Integer, Integer>> function1 = x -> y -> x + y;
        System.out.println(function1.apply(2).apply(3));

        // 柯里化
        Function<Integer, Function<Integer, Function<Integer, Integer>>> function2 = x -> y -> z -> x + y + z;
        System.out.println(function2.apply(2).apply(3).apply(4));

        // 高阶函数
        int[] nums = {2, 3, 4};
        Function f = function2;
        for (int i = 0; i < nums.length; i++) {
            if (f instanceof Function) {
                Object obj = f.apply(nums[i]);
                if (obj instanceof Function) {
                    f = (Function) obj;
                } else {
                    System.out.println("调用结束，结果为 " + obj);
                }
            }
        }
    }
}
