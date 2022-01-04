package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * EnumMap 是一个 Map 实现，专门将 Enum 作为其键
 * 何时使用EnumMap?
 * 使用 Enum 作为键可以进行一些额外的性能优化，例如更快的哈希计算，因为所有可能的键都是预先知道的。
 */
class _11EnumMapTest {
    
    @Test
    void usingEnumMap() {
        EnumMap<DayOfWeek, String> enumMap = new EnumMap<>(DayOfWeek.class);
        enumMap.put(DayOfWeek.MONDAY, "Soccer");
        
        assertEquals("Soccer", enumMap.get(DayOfWeek.MONDAY));
    }
}
