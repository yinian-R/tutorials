package com.wymm.fragment;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Java的泛型是伪泛型，这是因为Java在编译期间，所有的泛型信息都会被擦掉，这也就是通常所说类型擦除
 */
class _1GenericsModelTest {
    /**
     * 利用类型擦除
     */
    @Test
    void typeErasure() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(12);
        
        Method add = list.getClass().getDeclaredMethod("add", Object.class);
        add.invoke(list, "temp");
        
        System.out.println(list);
    }
    
    /**
     * 实例化泛型
     */
    @Test
    void instanceGenerics() {
        GenericsModel<Integer> genericsModel = new GenericsModel<>(123);
        assertEquals(123, genericsModel.getType());
    }
    
    /**
     * 实现泛型接口，不指定类型
     */
    static class GeneratorImpl<T> implements Generator<T> {
        
        @Override
        public T method() {
            return null;
        }
    }
    
    /**
     * 实现泛型接口，指定类型
     */
    static class GeneratorHasTypeImpl implements Generator<String> {
        
        @Override
        public String method() {
            return "hello";
        }
    }
    
}
