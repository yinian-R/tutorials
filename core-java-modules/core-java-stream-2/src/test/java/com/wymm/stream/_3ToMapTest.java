package com.wymm.stream;

import com.wymm.stream._3tomap.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 我们将讨论Collectors类的toMap()方法，我们将使用它将Stream收集到Map实例中
 *
 * <code>
 * Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
 * Function<? super T, ? extends U> valueMapper)
 * </code>
 */
public class _3ToMapTest {
    
    private static List<Book> BOOK_LIST;
    
    @BeforeAll
    static void init() {
        BOOK_LIST = new ArrayList<>();
        BOOK_LIST.add(new Book("The Fellowship of the Ring", 1954, "0395489318"));
        BOOK_LIST.add(new Book("The Two Towers", 1954, "0345339711"));
        BOOK_LIST.add(new Book("The Return of the King", 1955, "0618129111"));
    }
    
    /**
     * 使用 toMap 方法获取 Map 的键和值
     */
    @Test
    void whenConvertFromListToMap() {
        Map<String, String> convertToMap = BOOK_LIST.stream()
                .collect(Collectors.toMap(Book::getIsbn, Book::getName));
        
        System.out.println(convertToMap);
        assertEquals(3, convertToMap.size());
    }
    
    /**
     * 上面例子运行正常，但是重复键会看到异常 IllegalStateException
     * 我们引入一个合并函数，该函数在发生冲突的情况下，我们保留现有条目
     */
    @Test
    void whenMapHasDuplicateKey_without_merge_function_then_runtime_exception() {
        Map<Integer, Book> convertMap = BOOK_LIST.stream()
                .collect(Collectors.toMap(Book::getReleaseYear, Function.identity(), (existing, replacement) -> existing));
        
        assertEquals(2, convertMap.size());
        assertEquals("0395489318", convertMap.get(1954).getIsbn());
    }
    
    /**
     * 我们可以返回不同类型的 Map 实现
     * <code>
     * Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
     * Function<? super T, ? extends U> valueMapper,
     * BinaryOperator<U> mergeFunction,
     * Supplier<M> mapSupplier)
     * </code>
     */
    @Test
    void whenCreateConcurrentHashMap() {
        Map<Integer, Book> convertMap = BOOK_LIST.stream()
                .collect(Collectors.toMap(Book::getReleaseYear, Function.identity(),
                        (o1, o2) -> o1, ConcurrentHashMap::new));
        
        assertTrue(convertMap instanceof ConcurrentHashMap);
    }
    
    /**
     * 返回已排序的 Map。我们将使用 TreeMap 作为 mapSupplier 参数
     */
    @Test
    void whenMapIsSorted() {
        TreeMap<String, Book> convertMap = BOOK_LIST.stream()
                .collect(Collectors.toMap(Book::getName, Function.identity(), (o1, o2) -> o1, TreeMap::new));
        
        assertEquals("The Fellowship of the Ring", convertMap.firstKey());
    }
}
