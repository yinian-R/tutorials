package com.wymm.stream;

import java.util.stream.IntStream;

/**
 * 中间操作和终止操作
 * 惰性求值就是终止操作没有调用的情况下，中间操作不会执行
 */
public class _2StreamDemo {
    public static void main(String[] args) {
        int[] nums = {1,2,3};

        // map 是中间操作
        // sum 是终止操作
        int sum1 = IntStream.of(nums).map(_2StreamDemo::doubleNum).sum();
        System.out.println(sum1);


        IntStream.of(nums).map(_2StreamDemo::doubleNum);

    }

    private static int doubleNum(int i){
        System.out.println("执行乘以2");
        return i*2;
    }
}
