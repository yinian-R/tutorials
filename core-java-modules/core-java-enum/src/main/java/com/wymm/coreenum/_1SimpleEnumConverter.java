package com.wymm.coreenum;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过实现接口提供外部调用方法
 */
public enum _1SimpleEnumConverter implements Converter<Object, String> {
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
