package com.wymm.multipledb.web.restquery;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * key：用于保存字段名称，例如：firstName、age...
 * operation：用于保存操作，例如：等于、小于...
 * value：用于保存字段值，例如：john，25...
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
