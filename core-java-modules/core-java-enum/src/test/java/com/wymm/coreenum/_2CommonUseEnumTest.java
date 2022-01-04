package com.wymm.coreenum;

import com.wymm.coreenum.converter.DaysOfWeekEnum;
import org.junit.jupiter.api.Test;

/**
 * Enum 常用方法
 */
class _2CommonUseEnumTest {
    
    /**
     * 遍历 Enum
     */
    @Test
    void iteratingOverEnum() {
        for (DaysOfWeekEnum value : DaysOfWeekEnum.values()) {
            System.out.println(value);
        }
        
        // 扩展，遍历 Enum 使用 Stream 是个不错的选择
        DaysOfWeekEnum.stream().forEach(System.out::println);
    }
}
