package com.wymm.coreenum;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public enum SimpleEnumConverter implements Converter<Integer, String> {
    CSYS {
        Map<String, String> map = new HashMap<>();
        {
            map.put("temp", LocalDateTime.now().toString());
            System.out.println("just one run");
        }
        
        public String convert(Integer integer) {
            return "str:" + integer + " " + map.get("temp");
        }
    },
    VEHICLE_TYPE {
        public String convert(Integer integer) {
            return "temp";
        }
    }
}
