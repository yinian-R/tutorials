package com.wymm.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 合并 Stream
 */
public class _5MergeStreamTest {
    
    /**
     * 最简单方法是使用静态Stream.concat()方法
     */
    @Test
    void whenMergingStreams_thenResultStreamContainsElementsFromBoth() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        
        Stream<Integer> resultingStream = Stream.concat(stream1, stream2);
        
        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6),
                resultingStream.collect(Collectors.toList()));
    }
    
    /**
     * 合并多个Stream
     * 这种方法对于更多的 Stream 变得不可行
     */
    @Test
    void given3Streams_whenMerged_thenResultStreamContainsAllElements() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        Stream<Integer> stream3 = Stream.of(18, 15, 36);
        
        Stream<Integer> resultingStream = Stream.concat(
                Stream.concat(stream1, stream2), stream3);
        
        assertEquals(
                Arrays.asList(1, 3, 5, 2, 4, 6, 18, 15, 36),
                resultingStream.collect(Collectors.toList()));
    }
    
    /**
     * 我们有一个更好的选择，使用 Stream.of() 和 flatMap()
     */
    @Test
    public void given4Streams_whenMerged_thenResultStreamContainsAllElements() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        Stream<Integer> stream3 = Stream.of(18, 15, 36);
        Stream<Integer> stream4 = Stream.of(99);
        
        Stream<Integer> resultingStream = Stream.of(
                stream1, stream2, stream3, stream4)
                .flatMap(i -> i);
        
        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6, 18, 15, 36, 99),
                resultingStream.collect(Collectors.toList()));
    }
}
