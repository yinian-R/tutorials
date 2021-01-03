package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class _3ListOperationsTest {
    
    /**
     * 使用 Collectors.partitioningBy() 将 LIst 分成两个 List
     */
    @Test
    void givenList_whenParitioningIntoSublistsUsingPartitionBy_thenCorrect() {
        List<Integer> intList = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Map<Boolean, List<Integer>> groups = intList.stream().collect(Collectors.partitioningBy(v -> v > 6));
        assertEquals(IntStream.range(0, 7).boxed().collect(Collectors.toList()), groups.get(Boolean.FALSE));
        assertEquals(IntStream.range(7, 10).boxed().collect(Collectors.toList()), groups.get(Boolean.TRUE));
    }
    
    /**
     * 使用 Collectors.groupingBy() 将 List 进行分组
     */
    @Test
    void givenList_whenParitioningIntoNSublistsUsingGroupingBy_thenCorrect() {
        List<Integer> intList = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        Map<Integer, List<Integer>> map = intList.stream().collect(Collectors.groupingBy(v -> v / 3));
    }
    
    /**
     * 通过分隔符分割列表
     * 用“0”作为分隔符-我们首先获得列表中所有“0”元素的索引，然后在这些索引上拆分列表
     */
    @Test
    void givenList_whenSplittingBySeparator_thenCorrect() {
        List<Integer> intList = Stream.of(1, 2, 3, 0, 4, 5, 6, 0, 7, 8).collect(Collectors.toList());
        
        int[] indexes = Stream.of(
                IntStream.of(-1),
                IntStream.range(0, intList.size()).filter(i -> intList.get(i) == 0),
                IntStream.of(intList.size())
        )
                .flatMapToInt(s -> s)
                .toArray();
        
        List<List<Integer>> subSets = IntStream.range(0, indexes.length - 1)
                .mapToObj(i -> intList.subList(indexes[i] + 1, indexes[i + 1]))
                .collect(Collectors.toList());
        
        assertEquals(Stream.of(1, 2, 3).collect(Collectors.toList()), subSets.get(0));
        assertEquals(Stream.of(4, 5, 6).collect(Collectors.toList()), subSets.get(1));
        assertEquals(Stream.of(7, 8).collect(Collectors.toList()), subSets.get(2));
    }
    
    /**
     * 从列表中删除空值
     */
    @Test
    void givenListContainsNulls_whenFiltering_thenCorrect() {
        List<Integer> list = Stream.of(null, 1, null).collect(Collectors.toList());
        
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
        List<Integer> list = Stream.of(1, 2, 2).collect(Collectors.toList());
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
     * 获取两个列表的交集
     */
    @Test
    void intersectionLists() {
        List<String> list = Arrays.asList("red", "blue", "blue", "green", "red");
        List<String> otherList = Arrays.asList("red", "green", "green", "yellow");
        
        Set<String> result = list.stream()
                .distinct()
                .filter(otherList::contains)
                .collect(Collectors.toSet());
        
        Set<String> commonElements = new HashSet<>(Arrays.asList("red", "green"));
        
        assertEquals(commonElements, result);
    }
    
    /**
     * 计算 Arraylist 中的重复元素
     */
    @Test
    void count() {
        List<String> inputList = Stream.of(
                "expect1",
                "expect2", "expect2",
                "expect3", "expect3", "expect3",
                "expect4", "expect4", "expect4", "expect4"
        ).collect(Collectors.toList());
        // 使用 getOrDefault 计算。需要调用 put 和 getOrDefault 两个方法，相对复杂
        Map<String, Long> stringLongMap = countByForEachLoopWithGetOrDefault(inputList);
        // 使用 compute 计算。需要显式处理 null
        stringLongMap = countByForEachLoopWithMapCompute(inputList);
        // 使用 merge 计算
        stringLongMap = countByForEachLoopWithMapMerge(inputList);
        // 使用 Collectors.toMap() 计算。Collector使我们的代码紧凑且易于阅读，推荐
        stringLongMap = countByStreamToMap(inputList);
        // 使用 Collectors.groupingBy() 计算。Collector使我们的代码紧凑且易于阅读，推荐
        stringLongMap = countByStreamGroupBy(inputList);
    }
    
    public <T> Map<T, Long> countByForEachLoopWithGetOrDefault(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        inputList.forEach(e -> resultMap.put(e, resultMap.getOrDefault(e, 0L) + 1L));
        return resultMap;
    }
    
    public <T> Map<T, Long> countByForEachLoopWithMapCompute(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        inputList.forEach(o -> resultMap.compute(o, (k, v) -> v == null ? 1L : v + 1L));
        return resultMap;
    }
    
    public <T> Map<T, Long> countByForEachLoopWithMapMerge(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        inputList.forEach(o -> resultMap.merge(o, 1L, Long::sum));
        return resultMap;
    }
    
    public <T> Map<T, Long> countByStreamToMap(List<T> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }
    
    public <T> Map<T, Long> countByStreamGroupBy(List<T> inputList) {
        return inputList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
    
}
