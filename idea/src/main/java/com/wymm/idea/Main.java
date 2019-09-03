package com.wymm.idea;

import com.wymm.idea.annotation.Select;
import com.wymm.idea.builder.MapperBuilder;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        MapperBuilder builder = new MapperBuilder();
        builder.select();
        
        Class build = MapperBuilder.class;
        for (Method method : build.getMethods()) {
            if (method.isAnnotationPresent(Select.class)) {
                Select select = method.getAnnotation(Select.class);
                System.out.println("Select value: " + select.value());
                System.out.println(select);
            }
        }
        
        
        Method selectMethod = MapperBuilder.class.getDeclaredMethod("select");
        if (selectMethod.isAnnotationPresent(Select.class)) {
            Select select = selectMethod.getAnnotation(Select.class);
            System.out.println("Select value: " + select.value());
        }
    }
}
