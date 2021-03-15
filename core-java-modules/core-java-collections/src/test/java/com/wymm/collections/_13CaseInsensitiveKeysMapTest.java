package com.wymm.collections;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 不区分大小写键的 Map
 */
class _13CaseInsensitiveKeysMapTest {
    
    /**
     * TreeMap 是 NavigableMap 的实现，这意味着它始终根据给定的 Comparator 对条目进行排序。
     * 此外，TreeMap 使用 Comparator 来查找插入的键是重复键还是新建
     */
    @Test
    void usingTreeMap() {
        Map<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 1);
        treeMap.put("ABC", 2);
        
        assertEquals(1, treeMap.size());
        assertEquals(2, treeMap.get("aBc").intValue());
        assertEquals(2, treeMap.get("ABc").intValue());
        
        treeMap.remove("aBC");
        assertEquals(0, treeMap.size());
    }
    
    /**
     * Spring Core是一个Spring Framework模块，还提供实用程序类，包括LinkedCaseInsensitiveMap
     *
     * LinkedCaseInsensitiveMap包装一个LinkedHashMap，它是一个基于哈希表和链接列表的Map。
     * 与LinkedHashMap不同，它不允许插入空键。
     * LinkedCaseInsensitiveMap保留了键的原始顺序和原始大小写，同时允许在任何情况下调用诸如get和remove之类的函数。
     */
    @Test
    void usingLinkedCaseInsensitiveMap() {
        Map<String, Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 1);
        linkedHashMap.put("ABC", 2);
    
        assertEquals(1, linkedHashMap.size());
        assertEquals(2, linkedHashMap.get("aBc").intValue());
        assertEquals(2, linkedHashMap.get("ABc").intValue());
    
        linkedHashMap.remove("aBC");
        assertEquals(0, linkedHashMap.size());
    }
}
