package com.wymm.springboottemplate.module.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wymm.springboottemplate.module.manage.entity.BookType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 图书分类信息 Mapper 接口
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@Mapper
public interface BookTypeMapper extends BaseMapper<BookType> {

}
