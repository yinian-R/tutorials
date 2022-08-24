package com.wymm.springeasyexcelsample.web.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * (Book)表实体类
 *
 * @since 2022-08-10 15:03:14
 */
@Data
public class Book {
    
    private Long id;
    
    private String name;
    
    private String bookType;
    
    private String author;
    
    private String description;
    
    private LocalDateTime createTime;
    
}

