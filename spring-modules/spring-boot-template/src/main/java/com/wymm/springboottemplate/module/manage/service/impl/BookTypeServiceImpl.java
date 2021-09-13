package com.wymm.springboottemplate.module.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wymm.springboottemplate.module.manage.mapper.BookTypeMapper;
import com.wymm.springboottemplate.module.manage.entity.BookType;
import com.wymm.springboottemplate.module.manage.service.BookTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图书分类信息 服务实现类
 * </p>
 *
 * @author wymm
 * @since 2021-06-01
 */
@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType> implements BookTypeService {

}
