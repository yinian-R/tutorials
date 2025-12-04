package com.wymm.fragment;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class _4BeanTest {
    
    /**
     * 从实体对象中获取特定属性值
     */
    @Test
    void getProperty(){
        User obj = new User();
        obj.setUserName("meng");
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
    
        Object userName = wrapper.getPropertyValue("userName");
    
        System.out.println(userName);
    }
    
    /**
     * 在实体对象中设置特定属性值
     */
    @Test
    void setProperty(){
        String value = "meng";
        User obj = new User();
    
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        wrapper.setPropertyValue("userName", value);
    
        System.out.println(obj);
    }
    
    /**
     * 获取实体类中列表对象实际类型
     */
    @Test
    public void test1() {
        User user = new User();
        Class<?> zlass = user.getClass();
        Field[] declaredFields = zlass.getDeclaredFields();
        
        for (Field field : declaredFields) {
            Class<?> type = field.getType();
            
            System.out.println("type：" + type);
            
            if (type.equals(List.class) || type.equals(Collections.class)) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
                System.out.println("genericType：" + type);
            }
            System.out.println();
        }
    }
    
    @Data
    public static class User {
        private String id;
        private String userName;
        private List<Long> ids;
    }
    
}
