package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 不变 Map 实现
 */
class _12ImmutableMapTest {
    
    /**
     * 使用 Collections.unmodifiableMap 创建不可变 Map，它不允许直接对其修改。
     * 但是原本的 Map 依然可以修改，并且修改也会映射到不可变 Map 中
     */
    @Test
    void givenUsingTheJdk_whenUnmodifiableMapIsCreated_thenNotModifiable() {
        Map<String, String> mutableMap = new HashMap<>();
        mutableMap.put("USA", "North America");
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(mutableMap);
        
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("Canada", "North America"));
    
        mutableMap.remove("USA");
        assertFalse(unmodifiableMap.containsKey("USA"));
    
        mutableMap.put("Mexico", "North America");
        assertTrue(unmodifiableMap.containsKey("Mexico"));
    }
    
}
