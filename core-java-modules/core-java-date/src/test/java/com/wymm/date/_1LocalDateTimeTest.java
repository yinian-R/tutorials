package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * LocalDateTime表示日期时间。不包含时区的信息
 */
class _1LocalDateTimeTest {
    
    @Test
    void test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        String startDateStr = "2019-05-24 17:20:35";
        LocalDateTime startTime = LocalDateTime.parse(startDateStr, formatter);
        System.out.println(startTime.toString());
        
        // LocalDateTime 转换成字符串
        System.out.println(formatter.format(startTime));
        // LocalDateTime 转换成毫秒
        System.out.println(startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        // 当前时间转换成毫秒
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        
        
        System.out.println("系统当前日期时间：" +
                LocalDateTime.now()
        );
        
        System.out.println("特定日期时间：" +
                LocalDateTime.of(2019, 8, 28, 16, 10)
        );
        
        System.out.println("字符串日期时间：" +
                LocalDateTime.parse("2019-08-28T06:30:15")
        );
        
        System.out.println("字符串日期时间（格式非默认情况）：" +
                LocalDateTime.parse("2019-08-28 06:30:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        
        // 支持加/减年、月、天、分钟等
        System.out.println("\n加一天：" +
                LocalDateTime.now().plus(1, ChronoUnit.DAYS)
        );
        System.out.println("减一天：" +
                LocalDateTime.now().minus(1, ChronoUnit.DAYS)
        );
        
        
        // Getter 可以提取特定的单位，例如提取年
        System.out.println("\n获取年：" +
                LocalDateTime.now().getYear()
        );
    }
    
}