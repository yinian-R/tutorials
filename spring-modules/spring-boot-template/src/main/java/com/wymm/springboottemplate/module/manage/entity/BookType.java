package com.wymm.springboottemplate.module.manage.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 图书分类信息
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BookType extends Model<BookType> {
    
    
    /**
     * 图书分类编码
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String typeName;
    
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
}
