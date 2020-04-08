package stream;

import java.util.stream.IntStream;

/**
 * 外部迭代和内部迭代
 */
public class _1StreamDemo {
    public static void main(String[] args) {
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
