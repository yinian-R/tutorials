package com.wymm.stream;

import java.util.stream.Stream;

public class _4StreamDemo {
    public static void main(String[] args) {
        String str = "my name is word";

        // 把单词长度大于2的长度打印出来
        Stream.of(str.split(" ")).filter(s -> s.length() > 2).map(String::length).forEach(System.out::println);


    }
}
