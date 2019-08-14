package com.wymm.webflux2.utils;

import com.wymm.webflux2.exception.CheckException;

import java.util.stream.Stream;

public class CheckUtils {

    private static final String[] INVALID_NAMES = {"admin", "guanliyuan"};
    /**
     * 检验名字，不成功的时候抛出校验异常
     * @param value
     */
    public static void checkName(String value){
        Stream.of(INVALID_NAMES)
                .filter(name->name.equalsIgnoreCase(value))
                .findAny().ifPresent(name-> {
                    throw new CheckException("name", value);
        });
    }
}
