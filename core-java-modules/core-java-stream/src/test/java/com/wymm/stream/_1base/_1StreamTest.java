package com.wymm.stream._1base;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * 外部迭代和内部迭代
 */
class _1StreamTest {
    @Test
    void test() {
        int[] nums = {1, 2, 3};
        
        // 外部迭代
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        System.out.println("结果是" + sum);
        
        // 内部迭代，使用Stream的内部迭代
        int sum2 = IntStream.of(nums).sum();
        System.out.println("结果是" + sum2);
    }
}
