package com.wymm.coreenum;

import com.wymm.coreenum.converter._1SimpleEnumConverter;
import com.wymm.coreenum.converter._2SimpleEnumConverter;
import org.junit.jupiter.api.Test;

class _1ConverterEnumTest {
    /**
     * 通过实现接口提供外部调用方法
     */
    @Test
    void test() {
        String convert = _1SimpleEnumConverter.COLOR.convert("temp");
        System.out.println(convert);
        
        convert = _1SimpleEnumConverter.TYPE.convert(1);
        System.out.println(convert);
    }
    /**
     * 通过实现抽象方法提供外部调用方法
     */
    @Test
    void test2() {
        String convert = _2SimpleEnumConverter.COLOR.convert("temp");
        System.out.println(convert);
        
        convert = _2SimpleEnumConverter.TYPE.convert(1);
        System.out.println(convert);
    }
}