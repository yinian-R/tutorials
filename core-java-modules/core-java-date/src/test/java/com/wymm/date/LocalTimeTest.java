package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * LocalTime表示不带日期的时间
 */
class LocalTimeTest {
    
    @Test
    void test() {
        // 根据系统的时钟创建LocalTIme
        System.out.println("系统当前时间：" +
                LocalTime.now()
        );
        
        // HH:mm 格式的字符串可以使用 parse 方法
        System.out.println("\nHH:mm 格式的字符串的时间：" +
                LocalTime.parse("06:05")
        );
        
        // 使用 of 方法创建 LocalTime
        System.out.println("\n特定时间：" +
                LocalTime.of(6, 5)
        );
        
        System.out.println("\n加一小时：" +
                LocalTime.parse("06:06").plus(1, ChronoUnit.HOURS)
        );
        System.out.println("加一分钟：" +
                LocalTime.parse("06:06").plus(1, ChronoUnit.MINUTES)
        );
        System.out.println("减一小时：" +
                LocalTime.parse("06:06").minus(1, ChronoUnit.HOURS)
        );
        System.out.println("减一分钟：" +
                LocalTime.parse("06:06").minus(1, ChronoUnit.MINUTES)
        );
        
        // 各种getter方法可用于获取特定的时间单位，如小时，分钟和秒，如下所示获取小时：
        System.out.println("\n获取小时：" +
                LocalTime.parse("06:05").getHour()
        );
    
        // 判断时间先后（之前）
        System.out.println("\n判断时间先后（之前）：" +
                LocalTime.parse("05:00").isBefore(LocalTime.parse("06:00"))
        );
        // 判断时间先后（之后）
        System.out.println("判断时间先后（之后）：" +
                LocalTime.parse("06:00").isAfter(LocalTime.parse("05:00"))
        );
        
        // 一天中最大、最小、中午时间可以通过LocalTIme常量获取
        System.out.println("\n一天中最大的时间：" +
                LocalTime.MAX
        );
        System.out.println("一天中最小的时间：" +
                LocalTime.MIN
        );
        System.out.println("一天中中午的时间：" +
                LocalTime.NOON
        );
    }
}
