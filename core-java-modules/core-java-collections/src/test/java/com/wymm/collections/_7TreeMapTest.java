package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TreeMap是一种 Map 实现，可以根据其键的自然顺序对条目进行排序，或者如果在构建时提供了比较器，则使用比较器进行排序。
 */
class _7TreeMapTest {
    
    /**
     * TreeMap 默认是按自然顺序进行排序。对于整数意味着升序，对于字母意味着字母序
     */
    @Test
    public void givenTreeMap_whenOrdersEntriesNaturally_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");
    
        assertEquals("[1, 2, 3, 4, 5]", map.keySet().toString());
    }
    
    /**
     * TreeMap 中自定义排序
     */
    @Test
    public void givenTreeMap_whenOrdersEntriesByComparator_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");
        
        assertEquals("[5, 4, 3, 2, 1]", map.keySet().toString());
    }
    
    /**
     * TreeMap 排序的重要性
     * TreeMap将所有条目存储在已排序的顺序中。由于树状图的这一属性，我们可以执行类似的查询；找到“最大”，找到“最小”，找到所有小于或大于某个值的键，等等
     */
    @Test
    public void givenTreeMap_whenPerformsQueries_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");
        
        // 找到最大的值
        Integer highestKey = map.lastKey();
        // 找到最小的值
        Integer lowestKey = map.firstKey();
        // 找到前三的列表
        Set<Integer> keysLessThan3 = map.headMap(3).keySet();
        // 找到前三以外的列表
        Set<Integer> keysGreaterThanEqTo3 = map.tailMap(3).keySet();
        
        assertEquals(new Integer(5), highestKey);
        assertEquals(new Integer(1), lowestKey);
        assertEquals("[1, 2]", keysLessThan3.toString());
        assertEquals("[3, 4, 5]", keysGreaterThanEqTo3.toString());
    }
}
