package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class _6TransformTest {
    
    /**
     * LocalDateTime to Date
     */
    @Test
    void localDateTimeToDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        
        Date date = Date.from(instant);
        
        System.out.println(localDateTime);
        System.out.println(date);
    }
}
