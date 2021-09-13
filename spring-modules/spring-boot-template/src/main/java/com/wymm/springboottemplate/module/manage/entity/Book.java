package com.wymm.springboottemplate.module.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 图书信息
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@ApiModel(description = "图书信息")
@Data
@EqualsAndHashCode(callSuper = false)
public class Book extends Model<Book> {
    
    /**
     * 编码
     */
    @ApiModelProperty("编码")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 书名
     */
    @ApiModelProperty("书名")
    private String bookName;
    
    /**
     * 作者
     */
    @ApiModelProperty("作者")
    private String author;
    
    /**
     * 摘要
     */
    @ApiModelProperty("摘要")
    private String synopsis;
    
    /**
     * 出版日期
     */
    @ApiModelProperty("出版日期")
    private LocalDate publishedDate;
    
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
}
