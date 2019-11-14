package com.wymm.coreenum;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public enum SimpleEnumConverter implements Converter<Object, String> {
    CSYS {
        Map<String, String> map = new HashMap<>();
        {
            map.put("temp", LocalDateTime.now().toString());
            System.out.println("just one run");
        }
        
        @Override
        public String convert(Object object) {
            return "str:" + object + " " + map.get("temp");
        }
    },
    VEHICLE_TYPE {
        @Override
        public String convert(Object object) {
            return "temp：" + object;
        }
    }
}
