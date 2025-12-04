package com.wymm.cache.jetcache.model.qo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserQO {
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
}
