package com.example.springwebredis.web.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonStatisticsInfo {
    
    private Long id;
    
    private String code;
    
    private Integer totalNum;
    
    private LocalDateTime reportDate;
    
    private Integer reportPureDate;
    
    private Integer reportPureTime;
    
}
