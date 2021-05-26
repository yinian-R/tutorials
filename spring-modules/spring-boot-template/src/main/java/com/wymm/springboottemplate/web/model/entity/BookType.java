package com.wymm.springboottemplate.web.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
