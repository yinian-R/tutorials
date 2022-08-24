package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDate;
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
    
    /**
     * LocalDate to Date
     */
    @Test
    void localDateToDate() {
        LocalDate localDate = LocalDate.now();
        
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        
        Date date = Date.from(instant);
        
        System.out.println(localDate);
        System.out.println(date);
    }
    
    /**
     * Date to LocalDateTime
     */
    @Test
    void dateToLocalDateTime() {
        Date data= new Date();
    
        LocalDateTime localDateTime = data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        
        System.out.println(data);
        System.out.println(localDateTime);
    }
    
    /**
     * Date to LocalDate
     */
    @Test
    void dateToLocalDate() {
        Date data= new Date();
        
        LocalDate localDate = data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        System.out.println(data);
        System.out.println(localDate);
    }
    
    
}
