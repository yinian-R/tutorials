package com.wymm.log4j;

import com.wymm.log4j.logger.MockStackTraceInfo;
import org.junit.jupiter.api.Test;

/**
 * 模拟打印堆栈信息
 */
public class _5MockStackTraceTest {
    @Test
    public void print() {
        MockStackTraceInfo mockStackTraceInfo = new MockStackTraceInfo();
        mockStackTraceInfo.info();
    }
}
