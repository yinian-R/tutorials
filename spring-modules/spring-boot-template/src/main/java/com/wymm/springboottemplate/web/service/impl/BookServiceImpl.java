package com.wymm.springboottemplate.web.service.impl;

import com.wymm.springboottemplate.web.model.entity.Book;
import com.wymm.springboottemplate.web.mapper.BookMapper;
import com.wymm.springboottemplate.web.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图书信息 服务实现类
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
