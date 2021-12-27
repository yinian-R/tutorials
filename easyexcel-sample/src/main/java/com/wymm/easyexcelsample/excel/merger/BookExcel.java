package com.wymm.easyexcelsample.excel.merger;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BookExcel {
    
    @ExcelIgnore
    private Long bookTypeId;
    
    @ExcelProperty("分类")
    private String bookType;
    
    @ExcelIgnore
    private Long bookId;
    
    @ExcelProperty("书名")
    private String bookName;
    
    @ExcelProperty("作者")
    private String author;
}
