package com.wymm.coreenum.converter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过实现抽象方法提供外部调用方法
 */
public enum _2SimpleEnumConverter {
    COLOR {
        Map<Object, String> map = new HashMap<>();
        {
            map.put("temp", LocalDateTime.now().toString());
        }
        
        @Override
        public String convert(Object object) {
            return map.get(object);
        }
    },
    TYPE {
        @Override
        public String convert(Object object) {
            return "temp：" + object;
        }
    };
    
    public abstract String convert(Object object);
}
