package com.wymm.multipledb.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 图书信息
 * </p>
 *
 * @author wymm
 * @since 2021-07-18
 */
@Data
public class Book implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 编码
     */
    private Long id;
    
    /**
     * 书名
     */
    @TableField(value = "book_name")
    private String bookName;
    
    /**
     * 作者
     */
    private String author;
    
    /**
     * 摘要
     */
    private String synopsis;
    
    /**
     * 出版日期
     */
    @TableField(value = "published_date")
    private Date publishedDate;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    
}
