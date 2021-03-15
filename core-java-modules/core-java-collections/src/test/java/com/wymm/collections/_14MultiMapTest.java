package com.wymm.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 现有的Java Map实现都不允许Map处理单个key的多个值
 * Guava 处理单个key的多个值，有多种实现方式。
 * 我们可能要装饰尚未实现的 Map，Guava 有一个工厂方法允许我们执行此操作：Multimap.newMultimap()
 */
public class _14MultiMapTest {
    
    /**
     * 这个冗长的解决方案具有多个缺点，并且容易出错。
     * 这意味着我们要为每一个 Key 初始化一个 Collection，在添加和删除时检查其是否存在，在没有剩余值的情况下需手动将其删除，等等
     */
    @Test
    void simple() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        map.put("key1", list);
        map.get("key1").add("value1");
        map.get("key1").add("value2");
        
        assertEquals(map.get("key1").get(0), "value1");
        assertEquals(map.get("key1").get(1), "value2");
    }
    
    /**
     * 从 Java 8 开始，我们可以使用 compute() 对其进行改造
     * 尽管这是一个值得了解的地方，但是我们应该尽量避免它，除非有很好的理由不这么做，比如公司的限制性策略阻止我们使用第三方库
     */
    @Test
    void usingJava8Compute() {
        Map<String, List<String>> map = new HashMap<>();
        map.computeIfAbsent("key1", k -> new ArrayList<>()).add("value1");
        map.computeIfAbsent("key1", k -> new ArrayList<>()).add("value2");
        
        assertEquals(map.get("key1").get(0), "value1");
        assertEquals(map.get("key1").get(1), "value2");
    }
    
    /**
     * 使用 Guava 处理单个 key 的多个值
     * ArrayListMultimap，它对每个值使用由ArrayList支持的HashMap
     */
    @Test
    void usingGuavaArrayListMultimap() {
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("key1", "value2");
        map.put("key1", "value1");
        
        Collection<String> value = map.get("key1");
        assertTrue(value.containsAll(Arrays.asList("value2", "value1")));
    }
    
    /**
     * 使用 Guava 处理单个 key 的多个值
     * LinkedHashMultimap，保留键和值的插入顺序
     */
    @Test
    void usingGuavaLinkedHashMultimap() {
        Multimap<String, String> map = LinkedHashMultimap.create();
        map.put("key1", "value2");
        map.put("key1", "value1");
        
        Collection<String> value = map.get("key1");
        assertTrue(value.containsAll(Arrays.asList("value2", "value1")));
    }
    
    /**
     * 使用 Guava 处理单个 key 的多个值
     * TreeMultimap，以其自然顺序迭代键和值
     */
    @Test
    void usingGuavaLinkedTreeMultimap() {
        Multimap<String, String> map = TreeMultimap.create();
        map.put("key1", "value2");
        map.put("key1", "value1");
        
        Collection<String> value = map.get("key1");
        assertTrue(value.containsAll(Arrays.asList("value2", "value1")));
    }
}
