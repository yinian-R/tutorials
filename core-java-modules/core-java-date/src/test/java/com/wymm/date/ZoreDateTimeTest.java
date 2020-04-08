package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.*;

class ZoreDateTimeTest {
    
    @Test
    void test() {
        System.out.println("获取下“亚洲/上海”时区：" +
                ZoneId.of("Asia/Shanghai"))
        ;
        
        System.out.println("获取所有时区：" +
                ZoneId.getAvailableZoneIds()
        );
        
        // LocalDateTime 转换为特定时区内的时间
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Shanghai"));
        System.out.println("ZonedDateTime:" + zonedDateTime);
        
        
        // 使用parse解析特定时区的时间
        System.out.println("\n解析特定时区的时间:" +
                ZonedDateTime.parse("2019-08-28T18:01:42+08:00[Asia/Shanghai]")
        );
        
        // 通过创建ZoneOffset并为LocalDateTime实例设置
        LocalDateTime localDateTime = LocalDateTime.of(2019, 8, 28, 18, 10);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.of("+02:00"));
        System.out.println("使用ZoneOffset："+offsetDateTime);
    
        Instant now = Instant.now();
        System.out.println("时间戳："+now.toEpochMilli());
    
        Instant instant = Instant.ofEpochMilli(now.toEpochMilli());
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println("毫秒转换的时间："+localDateTime1);
    
    }
}
