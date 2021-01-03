package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TreeSet
 * 它存储唯一的元素
 * 它不保留插入的顺序
 * 它将元素按自然顺序升序排序
 * 它不是线程安全的
 * 它是由 TreeMap 支持
 */
class _4TreeSetTest {
    
    @Test
    void simple() {
        // 创建一个TreeSet的实例
        TreeSet<String> treeSet = new TreeSet<>();
        
        // 构造函数使我们可以使用 Comparable 或 Comparator 定义元素排序的顺序
        Set<String> treeSet2 = new TreeSet<>(Comparator.comparing(String::length));
        
        // 尽管 TreeSet 不是线程安全的，但可以使用 Collections.synchronizedSet() 包装器在外部对其进行同步
        Set<String> syncTreeSet = Collections.synchronizedSet(treeSet);
        
        // 添加元素。如果添加了元素，则该方法返回 true，否则返回 false
        // 该 add 方法是非常重要的，因为该方法的实现细节说明了如何 TreeSet 的内部工作，它如何利用 TreeMap 中的 put 方法来存储元素
        // TreeSet 在内部实现是使用 TreeMap
        assertTrue(treeSet.add("String Added"));
        
        
        treeSet = new TreeSet<>();
        treeSet.add("First");
        // 获取第一个元素。没有元素会异常 NoSuchElementException
        assertEquals("First", treeSet.first());
        
        treeSet.add("Last");
        // 获取最后一个元素。没有元素会异常 NoSuchElementException
        assertEquals("Last", treeSet.last());
    }
    
    @Test
    void whenUsingSubSet_shouldReturnSubSetElements() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(3);
        treeSet.add(2);
        treeSet.add(4);
        treeSet.add(5);
        treeSet.add(6);
        
        Set<Integer> expectedSet = new TreeSet<>();
        expectedSet.add(2);
        expectedSet.add(3);
        expectedSet.add(4);
        expectedSet.add(5);
        
        // 此方法将返回从 fromElement 到 toElement 的元素。请注意，fromElement 是包含的，toElement 是排除的
        Set<Integer> subSet = treeSet.subSet(2, 6);
        assertEquals(expectedSet, subSet);
        
        // 此方法将返回小于指定元素的 TreeSet 元素
        Set<Integer> subSet2 = treeSet.headSet(6);
        assertEquals(subSet2, treeSet.subSet(1, 6));
        
        // 此方法将返回大于或等于指定元素的 TreeSet 元素
        Set<Integer> subSet3 = treeSet.tailSet(3);
        assertEquals(subSet3, treeSet.subSet(3, true, 6, true));
    }
    
    /**
     * TreeSet 不能插入 null
     * 在Java 7之前，可以将null元素添加到空的 TreeSet 中
     * 但是，这被认为是一个错误。因此，TreeSet不再支持添加null。
     * 当我们将元素添加到 TreeSet 时，元素将根据其自然顺序或由比较器指定的顺序进行排序。
     * 因此，与现有元素相比，添加 null 会导致 NullPointerException，因为无法将 null 与任何值进行比较
     */
    @Test
    void whenAddingNullToNonEmptyTreeSet_shouldThrowException() {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        // exception
        treeSet.add(null);
    }
    
}
