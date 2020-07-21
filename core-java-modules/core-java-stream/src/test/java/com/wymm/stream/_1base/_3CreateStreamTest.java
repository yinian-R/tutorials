package com.wymm.stream._1base;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流的创建
 */
class _3CreateStreamTest {
    @Test
    void test() {
        List<String> list = new ArrayList<>();
        
        // 从集合创建
        list.stream();
        list.parallelStream(); // 并行流
        
        // 从数组创建
        Arrays.stream(new int[]{1, 2, 3});
        
        // 创建数字流
        IntStream.of(1, 2, 3);
        IntStream.rangeClosed(1, 10);
        
        // 使用random创建一个无限流
        new Random().ints().limit(10);
        
        // 自己创建流。假设使用Random提供数据
        Random random = new Random();
        Stream.generate(() -> random.nextInt()).limit(20);
    }
}
