package com.wymm.stream._1base;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 终止操作
 */
class _5StreamTest {
    @Test
    void main() {
        String str = "my name is 007";
        
        // 使用并行流
        str.chars().parallel().forEach(i -> System.out.println((char) i));
        System.out.println("~~~~~~~~~~");
        // 使用并行流，确保顺序
        str.chars().parallel().forEachOrdered(i -> System.out.println((char) i));
        
        // 收集到List
        List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(list);
        
        // 使用reduce拼接字符串
        Optional<String> letters = Stream.of(str.split(" ")).reduce((s1, s2) -> s1 + "|" + s2);
        System.out.println(letters.orElse(""));
        
        // 使用reduce拼接字符串，带初始值
        String letters2 = Stream.of(str.split(" ")).reduce("", (s1, s2) -> s1 + "|" + s2);
        System.out.println(letters2);
        
        // 使用reduce计算所有单词的总长度
        Integer length = Stream.of(str.split(" ")).map(String::length).reduce(0, Integer::sum);
        System.out.println(length);
        
        // max的使用
        Optional<String> max = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        System.out.println(max.get());
        
        OptionalInt findFirst = new Random().ints().findAny();
        System.out.println(findFirst.getAsInt());
    }
    
    
}
