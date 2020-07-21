package com.wymm.fragment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _2GenericsTest {
    
    /**
     * 使用泛型方法
     */
    @Test
    void whenGenericsMethod() {
        Integer[] intArray = {1, 2, 3, 4, 5};
        // 数组转换列表
        List<Integer> list = Generics.fromArrayToList(intArray);
        
        // 数组转换列表，并转换类型
        List<String> stringList = Generics.fromArrayToList(intArray, Object::toString);
        assertEquals(stringList, Stream.of("1", "2", "3", "4", "5").collect(Collectors.toList()));
    }
    
    /**
     * 使用上界通配符
     */
    @Test
    void test() {
        Integer[] intArray = {1, 2, 3, 4, 5};
        List<Integer> list = Generics.fromArrayToList(intArray);
    }
    
    /**
     * 对泛型使用通配符 "?"
     */
    @Test
    void test2() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new GoodBuilding());
        
        List<GoodBuilding> buildings2 = new ArrayList<>();
        
        
        Generics.paintAllBuildings(buildings);
        // Compilation error
        //Generics.paintAllBuildings(buildings2);
    
    
        Generics.paintAllBuildings2(buildings);
        // good
        Generics.paintAllBuildings2(buildings2);
    }
    
}
