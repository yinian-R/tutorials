package com.wymm.springboottemplate.web.service.impl;

import com.wymm.springboottemplate.web.model.entity.BookType;
import com.wymm.springboottemplate.web.mapper.BookTypeMapper;
import com.wymm.springboottemplate.web.service.IBookTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图书分类信息 服务实现类
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType> implements IBookTypeService {

}
