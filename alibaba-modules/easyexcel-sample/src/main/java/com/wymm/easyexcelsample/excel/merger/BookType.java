package com.wymm.easyexcelsample.excel.merger;

import lombok.Data;

import java.util.List;

@Data
public class BookType {
    
    private Long bookTypeId;
    
    private String bookType;
    
    private List<Book> books;
}
