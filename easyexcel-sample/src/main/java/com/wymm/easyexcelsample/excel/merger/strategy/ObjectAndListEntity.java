package com.wymm.easyexcelsample.excel.merger.strategy;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于对象中嵌套列表的结构合并导出
 */
@Setter
@Getter
public abstract class ObjectAndListEntity {
    /**
     * 唯一标识
     * 需要合并在一起的行具有相同的标识
     */
    @ExcelIgnore
    private Object unique;
    
    /**
     * 合并的行数
     */
    @ExcelIgnore
    private Integer mergeRowNum;
}
