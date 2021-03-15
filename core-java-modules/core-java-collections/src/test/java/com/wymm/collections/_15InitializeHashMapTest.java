package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 初始化 HashMap
 */
class _15InitializeHashMapTest {
    
    /**
     * 使用双括号语法来初始化 Map
     * 我们应该尽量避免使用这种初始化技术，因为它在每次使用时都会创建一个匿名的额外类，保留对封闭对象的隐藏引用，并可能导致内存泄漏问题
     */
    @Test
    void usingDoubleBraceInitializeHashMap() {
        Map<String, String> doubleBraceMap = new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }};
    }
    
    /**
     * 创建一个单例不可变地图
     */
    @Test
    void createSingletonMap() {
        Map<String, String> map = Collections.singletonMap("username1", "password1");
    }
    
    /**
     * 创建一个不变的空地图
     */
    @Test
    void createEmptyMap() {
        Map<String, String> emptyMap = Collections.emptyMap();
    }
    
    /**
     * 使用二维的 String 数组的 Stream 并将它们搜集到 Map 中
     */
    @Test
    void usingCollectors() {
        Map<String, String> map1 = Stream.of(new String[][]{
                {"Hello", "World"},
                {"John", "Doe"},
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        
        // 为了更通用，让我们采用 Objects 数组并执行相同操作
        Map<String, Integer> map2 = Stream.of(new Object[][]{
                {"data1", 1},
                {"data2", 2},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
    }
}
