package com.wymm.coreenum;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过实现抽象方法提供外部调用方法
 */
public enum _2SimpleEnumConverter {
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
	};
	
	public abstract String convert(Object object);
}
