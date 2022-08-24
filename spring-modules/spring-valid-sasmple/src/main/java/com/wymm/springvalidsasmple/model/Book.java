package com.wymm.springvalidsasmple.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import lombok.Data;

import java.io.Serializable;

/**
 * @since 2022-08-22
 */
@Data
public class Book implements Serializable {
    
    @NotBlank(message = "书名不能为空", groups = Default.class)
    private String name;
    
    @NotNull(message = "年龄不能为空", groups = Default.class)
    private Integer age;
    
    @NotBlank(message = "{'未知分类：【' + bookTypeText + '】'}", groups = DictGroup.class)
    private String bookType;
    
    @NotBlank(message = "分类不能为空", groups = Default.class)
    private String bookTypeText;
    
    
    public interface DictGroup {
    }
}
