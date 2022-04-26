package com.wymm.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 主要介绍 map() 和 flatMap()
 * map()和flatMap() API 源于函数式语言。在 Java 8 中，我们可以在Optional、Stream和CompletableFuture中找到它们
 */
public class _6MapAndFlatMapTest {
    
    /**
     * Optionals 中的 Map 和 Flatmap
     */
    @Test
    void optional() {
        // map()方法与Optional配合得很好，会返回确切的类型
        Optional<String> str = Optional.of("test");
        assertEquals(Optional.of("TEST"), str.map(String::toUpperCase));
        
        // 在更复杂的情况下，使用 map() 会导致嵌套结构，因为 map() 实现会在内部进行额外的包装
        // 如我们所见，我们最终得到了嵌套结构 Optional<Optional<String>>。虽然它可以工作，但使用起来非常麻烦，并且不提供任何额外的 null 安全性，因此最好保持扁平结构。
        assertEquals(Optional.of(Optional.of("STRING")),
                Optional.of("string")
                        .map(s -> Optional.of("STRING")));
        
        // 这时候正是 flatMap() 帮助我们做的事情
        assertEquals(Optional.of("STRING"), Optional
                .of("string")
                .flatMap(s -> Optional.of("STRING")));
    }
    
    /**
     * map()方法将底层序列包装在Stream实例中，而flatMap()方法允许避免嵌套Stream<Stream<R>>结构
     */
    @Test
    void steams() {
        List<List<String>> list = Arrays.asList(
                Arrays.asList("a"),
                Arrays.asList("b"));
        System.out.println(list);
        
        System.out.println(list
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }
}
