package com.wymm.springeasyexcelsample.web.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.data.WriteCellData;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * (Book)表实体类
 *
 * @since 2022-08-10 15:03:14
 */
@Data
public class BookColorStyleExcel {
    
    @NotBlank(message = "书名不能为空")
    @ExcelProperty("书名")
    private String name;
    
    @ExcelProperty("分类")
    private WriteCellData<String> bookType;
    
    @ExcelProperty("作者")
    private String author;
    
    @ExcelProperty("描述")
    private String description;
    
}

