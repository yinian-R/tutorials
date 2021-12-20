package com.wymm.easyexcelsample.excel.merger;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BackRouteDTO {
    
    @ExcelProperty("-observerId")
    private Integer observerId;
    
    @ExcelProperty("-personCode")
    private String personCode;
    
    @ExcelProperty("-personName")
    private String personName;
    
    @ExcelProperty("-identityTypeCollect")
    private String identityTypeCollect;
    
    @ExcelProperty("-identityTypeCollectView")
    private String identityTypeCollectView;
    
    @ExcelProperty("-identityNumberCollect")
    private String identityNumberCollect;
    
}