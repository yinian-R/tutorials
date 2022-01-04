package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _6HashMapTest {
    
    
    @Test
    void simple() {
        Map<String, Object> map = new HashMap<>();
        
        // HashMap 还允许我们将 null 作为键，而 TreeMap 不支持 null 作为键
        map.put(null, null);
        
        // 检查映射中是否存在键
        map.containsKey("E-Bike");
        // 检查映射中是否存在值
        map.containsValue("default");
        // 尽管它们看起来非常相似，但是这两个方法调用之间在性能上存在重要差异。检查键是否存在的复杂度为O（1），而检查元素的复杂度为O（n），因为有必要遍历映射中的所有元素
    }
    
    /**
     * 遍历 HashMap
     */
    @Test
    void iterateHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        // 使用 keySet() 遍历
        for (String key : map.keySet()) {
            Object value = map.get(key);
        }
        // 使用 entrySet() 遍历
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
        }
        // 使用 Java 8 forEach 遍历
        map.forEach((key, value) -> {
            
        });
    }
    
    /**
     * 使用 GetOrDefault()方法，从键映射中获取值，或者在没有给定键映射的情况下返回默认值
     */
    @Test
    void usingGetOrDefault() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Java", "1.8");
        Object java = map.getOrDefault("Java", "1.9");
        Object javaScript = map.getOrDefault("JavaScript", "2018");
        assertEquals(java, "1.8");
        assertEquals(javaScript, "2018");
    }
    
    /**
     * 使用 putIfAbsent()方法，可以添加一个新的映射，前提是键还没有映射
     */
    @Test
    void usingPutIfAbsent() {
        HashMap<String, Object> map = new HashMap<>();
        map.putIfAbsent("java", "1.8");
    }
    
    /**
     * 使用 merge()方法, 如果存在映射，我们可以通过旧值和新值修改给定键的值，否则可以添加新值
     */
    @Test
    void usingMerge() {
        HashMap<String, Integer> map = new HashMap<>();
        map.merge("count", 1, Integer::sum);
    }
    
    /**
     * 使用 compute()方法，可以计算给定键的值
     * 使用 computeIfAbsent()方法，当键没有映射时计算给定键的值
     * 使用 computeIfPresent()方法，当键有映射时计算给定键的值
     */
    @Test
    void usingCompute() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("version", 1);
        
        map.compute("version", (k, v) -> {
            if (v != null) {
                return v + 1;
            } else {
                return 1;
            }
        });
        
        map.computeIfAbsent("version", k -> {
            return 0;
        });
        
        map.computeIfPresent("version", (k, v) -> {
            return v + 1;
        });
    }
}
