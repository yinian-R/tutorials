package com.wymm.idea;

import com.wymm.idea.annotation.Select;
import com.wymm.idea.builder.MapperBuilder;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationTest {
    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapperBuilder builder = new MapperBuilder();
        builder.select();
        
        // 遍历所有的注解，获取属性
        Class build = MapperBuilder.class;
        for (Method method : build.getMethods()) {
            if (method.isAnnotationPresent(Select.class)) {
                Select select = method.getAnnotation(Select.class);
                System.out.println("Select value: " + select.value());
                System.out.println(select);
            }
        }
        
        // 获取具体的注解，获取属性
        Method selectMethod = MapperBuilder.class.getDeclaredMethod("select");
        if (selectMethod.isAnnotationPresent(Select.class)) {
            Select select = selectMethod.getAnnotation(Select.class);
            System.out.println("Select value: " + select.value());
        }
        
        // 获取具体的注解，获取属性（耦合低）
        Annotation selectAnnotation = MapperBuilder.class.getDeclaredMethod("select").getAnnotation(Select.class);
        String value = (String) selectAnnotation.getClass().getMethod("value").invoke(selectAnnotation);
        System.out.println("value: " + value);
    }
}
