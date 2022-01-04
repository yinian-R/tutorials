package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


/**
 * HashSet
 * 它存储唯一元素并且允许为 null
 * 它是由 HashMap 支持
 * 它不保持插入顺序
 * 它不是线程安全的
 */
class _5HashSetTest {
    
    @Test
    void simple() {
        Set<String> hashset = new HashSet<>();
        // 添加元素。仅当元素不存在于集合中时才添加元素。如果添加了元素，则该方法返回true，否则返回false
        // 从实现的角度来看，add 方法是非常重要的一种。实现细节详细说明了 HashSet 在内部如何工作以及如何利用 HashMap 的 put 方法
        assertTrue(hashset.add("String Added"));
        
        // 从集合中删除指定的元素（如果存在）。如果集合包含指定的元素，则此方法返回true
        assertTrue(hashset.remove("String Added"));
        
        // 从集合中删除所有项目
        hashset.clear();
        
        // 识别集合中存在的元素数量
        assertEquals(0, hashset.size());
    
        // 该方法返回 Set 中元素的迭代器。元素没有特别的顺序访问，并且迭代器是快速失败的
        Iterator<String> itr = hashset.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        
    }
    
}
