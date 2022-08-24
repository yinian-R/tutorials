package com.wymm.springeasyexcelsample.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wymm.springeasyexcelsample.web.entity.Book;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Book)表数据库访问层
 *
 * @since 2022-08-10 15:03:14
 */
@Mapper
public interface BookDao extends BaseMapper<Book> {

}

