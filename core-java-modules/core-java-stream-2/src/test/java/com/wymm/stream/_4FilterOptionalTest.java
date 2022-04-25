package com.wymm.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 从 Stream 中过滤 Optionals 空值
 */
public class _4FilterOptionalTest {
    static List<Optional<String>> LIST_OF_OPTIONALS = Arrays.asList(
            Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));
    
    /**
     * 过滤 Optionals 空值
     */
    @Test
    void filterOptionalEmpty() {
        // 方式一：使用过滤器
        List<String> list = LIST_OF_OPTIONALS.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        
        // 方式二：使用 flatMap() 方法
        List<String> list2 = LIST_OF_OPTIONALS.stream()
                .flatMap(o -> o.map(Stream::of).orElse(Stream.empty()))
                .collect(Collectors.toList());
    }
}
