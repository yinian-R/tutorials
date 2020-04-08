package com.wymm.date;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Java 8 API: LocalDateTime
 * LocalDateTime 不包含任何时区的信息
 */
public class LocalDateTimeDemo {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static void main(String[] args) {
        String startDateStr = "2019-05-24 17:20:35";
        LocalDateTime startTime = LocalDateTime.parse(startDateStr, formatter);
        System.out.println(startTime.toString());
        
        // LocalDateTime 转换成字符串
        System.out.println(formatter.format(startTime));
        // LocalDateTime 转换成毫秒
        System.out.println(startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        // 当前时间转换成毫秒
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }
    
}
