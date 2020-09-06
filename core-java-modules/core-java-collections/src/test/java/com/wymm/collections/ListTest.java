package com.wymm.collections;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ListTest {
    
    /**
     * 获取不可变列表
     */
    @Test
    public void givenUsingTheJdk_whenUnmodifiableListIsCreated_thenNotModifiable() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        // exception
        unmodifiableList.add("four");
    }
    
    /**
     * 将迭代器转换为列表
     */
    @Test
    void iteratorTransformList() {
        Iterator<Integer> iterator = Arrays.asList(1, 2, 3).iterator();
        List<Integer> actualList = new ArrayList<Integer>();
        
        // 使用 while
        while (iterator.hasNext()) {
            actualList.add(iterator.next());
        }
        
        // 使用 Java 8 Iterator.forEachRemaining
        iterator.forEachRemaining(actualList::add);
        
        // 使用 Java 8 Streams API
        Iterable<Integer> iterable = () -> iterator;
        actualList = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    
    /**
     * 从列表中删除空值
     */
    @Test
    void givenListContainsNulls_whenFiltering_thenCorrect() {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(1);
        list.add(null);
        
        // 使用方法 removeIf 删除null。注意，此方案会修改原始列表
        list.removeIf(Objects::isNull);
        
        // 使用 while。注意，此方案会修改原始列表
        while (list.remove(null)) ;
        
        // 使用 Java8 Stream 删除null
        List<Integer> listWithoutNulls = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    /**
     * 删除特定元素，int基础类型时需转换为包装类
     */
    @Test
    void givenListContainsElements_whenFiltering_thenCorrect() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        
        int element = 2;
        
        // 使用方法 removeIf 删除特定元素。注意，此方案会修改原始列表
        list.removeIf(v -> Objects.equals(v, element));
        
        // 使用 Java8 Stream 删除特定元素
        List<Integer> listWithoutNulls = list.stream()
                .filter(v -> !Objects.equals(v, element))
                .collect(Collectors.toList());
    }
    
    /**
     * 删除列表重复数据
     */
    @Test
    void givenListContainsDuplicates_whenRemovingDuplicatesWithJava8_thenCorrect() {
        List<Integer> listWithDuplicates = new ArrayList<>();
        listWithDuplicates.add(1);
        listWithDuplicates.add(1);
        listWithDuplicates.add(2);
        
        // 使用 Java 8 Stream
        List<Integer> listWithoutDuplicates = listWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        
        // 使用 Java Set
        listWithoutDuplicates = new ArrayList<>(new TreeSet<>(listWithDuplicates));
    
    }
}
