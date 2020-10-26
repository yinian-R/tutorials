package com.wymm.fragment;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generics {
    
    /**
     * 泛型方法，定义数组通用转换列表的方法
     */
    public static <T> List<T> fromArrayToList(T[] array) {
        return Stream.of(array).collect(Collectors.toList());
    }
    
    /**
     * 泛型方法，定义数组通用转换列表的方法。处理类型的T转换G
     */
    public static <T, G> List<G> fromArrayToList(T[] array, Function<T, G> function) {
        return Stream.of(array)
                .map(function)
                .collect(Collectors.toList());
    }
    
    /**
     * 有界泛型：类型参数可以是有界的。有界表示“ 限制 ”，我们可以限制方法可以接受的类型。
     * 上界通配符 <? extends E>
     * 指定一个方法接受一个类型及其所有子类（上限）
     * <p>
     * 一个类型可以指定多个上限:
     * <T extends Number & Comparable>
     */
    public static <T extends Number> List<T> fromArrayToList(T[] array) {
        return Stream.of(array).collect(Collectors.toList());
    }
    
    /**
     * 下界通配符 <? super E>
     * 指定一个方法接受一个类型及其超类（下限）
     */
    public static void addDogs(List<? super Animal> list) {
        list.add(new Dog());
    }
    
    
    /**
     * 未指定泛型
     * 此时 buildings 仅适用定义是Building的List
     */
    public static void paintAllBuildings(List<Building> buildings) {
        buildings.forEach(Building::paint);
    }
    
    /**
     * 使用上界通配符
     * 此时，方法适用所有 Building 及子类
     */
    public static void paintAllBuildings2(List<? extends Building> buildings) {
        buildings.forEach(Building::paint);
    }
    
    
    static class Animal {
        
    }
    
    static class Dog extends Animal {
        
    }
}
