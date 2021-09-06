package com.wymm.springboottemplate.module.manage.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookDTO {
    
    private Long id;
    
    private String bookName;
}
