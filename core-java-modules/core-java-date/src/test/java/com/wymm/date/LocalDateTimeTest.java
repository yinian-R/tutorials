package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * LocalDateTime表示日期时间。不包含时区的信息
 */
class LocalDateTimeTest {
    
    @Test
    void test() {
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