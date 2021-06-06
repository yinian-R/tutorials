package com.wymm.springboottemplate.module.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wymm.springboottemplate.module.manage.model.entity.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 图书信息 Mapper 接口
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
