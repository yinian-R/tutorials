package com.wymm.stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Predicate 示例
 */
public class _8PredicateTest {
    
    List<String> names = Arrays.asList("Adam", "Alexander", "John", "Tom");
    
    /**
     * 基本示例
     */
    @Test
    public void whenFilterList_thenSuccess() {
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        
        assertEquals(2, result.size());
        assertTrue(result.contains("Adam"));
        assertTrue(result.contains("Alexander"));
    }
    
    /**
     * 多个过滤器
     */
    @Test
    public void whenFilterListWithMultipleFilters_thenSuccess(){
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A"))
                .filter(name -> name.length() < 5)
                .collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertTrue(result.contains("Adam"));
    }
    
    /**
     * 复杂谓词
     */
    @Test
    public void whenFilterListWithComplexPredicate_thenSuccess(){
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A") && name.length() < 5)
                .collect(Collectors.toList());
    
        assertEquals(1, result.size());
        assertTrue(result.contains("Adam"));
    }
    
    /**
     * 组合谓词 and()
     */
    @Test
    public void whenFilterListWithCombinedPredicatesUsingAnd_thenSuccess(){
        Predicate<String> predicate1 =  str -> str.startsWith("A");
        Predicate<String> predicate2 = str -> str.length() < 5;
    
        List<String> result = names.stream()
                .filter(predicate1.and(predicate2))
                .collect(Collectors.toList());
    
        assertEquals(1, result.size());
        assertTrue(result.contains("Adam"));
    }
    
    /**
     * 组合谓词 or()
     */
    @Test
    public void whenFilterListWithCombinedPredicatesUsingOr_thenSuccess(){
        Predicate<String> predicate1 =  str -> str.startsWith("J");
        Predicate<String> predicate2 =  str -> str.length() < 4;
        
        List<String> result = names.stream()
                .filter(predicate1.or(predicate2))
                .collect(Collectors.toList());
        
        assertEquals(2, result.size());
        assertTrue(result.contains("John"));
        assertTrue(result.contains("Tom"));
    }
    
    /**
     * 组合谓词 negate()
     */
    @Test
    public void whenFilterListWithCombinedPredicatesUsingOrAndNegate_thenSuccess(){
        Predicate<String> predicate1 =  str -> str.startsWith("J");
        Predicate<String> predicate2 =  str -> str.length() < 4;
        
        List<String> result = names.stream()
                .filter(predicate1.or(predicate2.negate()))
                .collect(Collectors.toList());
        
        assertEquals(3, result.size());
        assertTrue(result.contains("Adam"));
        assertTrue(result.contains("Alexander"));
        assertTrue(result.contains("John"));
    }
    
    /**
     * 组合谓词 内联
     */
    @Test
    public void whenFilterListWithCombinedPredicatesInline_thenSuccess(){
        List<String> result = names.stream()
                .filter(((Predicate<String>)name -> name.startsWith("A"))
                        .and(name -> name.length()<5))
                .collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertTrue(result.contains("Adam"));
    }
    
    /**
     * 通过集合来减少谓词 and()
     */
    @Test
    public void whenFilterListWithCollectionOfPredicatesUsingAnd_thenSuccess(){
        List<Predicate<String>> allPredicates = new ArrayList<>();
        allPredicates.add(str -> str.startsWith("A"));
        allPredicates.add(str -> str.contains("d"));
        allPredicates.add(str -> str.length() > 4);
        
        List<String> result = names.stream()
                .filter(allPredicates.stream().reduce(x->true, Predicate::and))
                .collect(Collectors.toList());
        
        assertEquals(1, result.size());
        assertTrue(result.contains("Alexander"));
    }
    
    /**
     * 通过集合来减少谓词 or()
     */
    @Test
    public void whenFilterListWithCollectionOfPredicatesUsingOr_thenSuccess(){
        List<Predicate<String>> allPredicates = new ArrayList<>();
        allPredicates.add(str -> str.startsWith("A"));
        allPredicates.add(str -> str.contains("d"));
        allPredicates.add(str -> str.length() > 4);
        
        List<String> result = names.stream()
                .filter(allPredicates.stream().reduce(x->false, Predicate::or))
                .collect(Collectors.toList());
        
        assertEquals(2, result.size());
        assertTrue(result.contains("Adam"));
        assertTrue(result.contains("Alexander"));
    }
}
