package com.wymm.collections;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ArrayList 是构建在数组之上的 List 实现之一，它可以在添加/删除元素时动态增长和收缩。通过从0开始的索引可以轻松访问元素
 * 随机访问需要 O(1) 时间
 * 加元素需要时间 O(1) 时间
 * 插入/删除需要 O(n) 时间
 * 搜索需要 O(n) 时间来处理未排序的数组，而要花费 O(log n) 来处理已排序的数组
 */
class _2ArrayListTest {
    
    @Test
    void simple() {
        List<Long> list = new ArrayList<>();
        // 在结尾插入元素
        list.add(1L);
        list.add(2L);
        // 在特定位置插入元素
        list.add(1, 3L);
        assertEquals(Arrays.asList(1L, 3L, 2L), list);
        
        
        List<Long> list2 = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        // 可以一次插入一个集合或几个元素
        LongStream.range(4, 10).boxed()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(ArrayList::new),
                                ys -> list2.addAll(0, ys)
                        )
                );
        assertEquals(Arrays.asList(4L, 5L, 6L, 7L, 8L, 9L, 1L, 2L, 3L), list2);
        
        
        List<Integer> list3 = new ArrayList<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new))
        );
        // 有两种可用的迭代器：Iterator 和 ListIterator
        // 前者使您有机会在一个方向上遍历该列表，而后者则使您可以在两个方向上遍历该列表
        ListIterator<Integer> it = list3.listIterator(list3.size());
        List<Integer> result = new ArrayList<>(list3.size());
        while (it.hasPrevious()) {
            result.add(it.previous());
        }
        Collections.reverse(list3);
        assertEquals(result, list3);
    
    
        List<Integer> list4 = new ArrayList<>(
                IntStream.range(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new))
        );
        Collections.reverse(list4);
    
        // 删除 Integer 列表的元素，使用 int 会将索引删除
        list4.remove(0);
        assertEquals(list4.get(0), 8);
        // 删除 Integer 列表的元素，应先把 int 转换成 Integer
        list4.remove(Integer.valueOf(0));
        assertFalse(list4.contains(0));
    
        
        // 获取随机元素列表
        Collections.shuffle(list4);
    }
    
    /**
     * 生成不可变 ArrayList
     */
    @Test
    void givenUsingTheJdk_whenUnmodifiableListIsCreated_thenNotModifiable() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("four"));
    }
    
    @Test
    void  test(){
        List<Integer> givenList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Collections.shuffle(givenList);
        System.out.println();
    }
    
}
