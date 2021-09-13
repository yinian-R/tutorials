package com.wymm.springboottemplate.module.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wymm.springboottemplate.module.manage.mapper.BookMapper;
import com.wymm.springboottemplate.module.manage.entity.Book;
import com.wymm.springboottemplate.module.manage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 图书信息 服务实现类
 * </p>
 *
 * @author wymm
 * @since 2021-06-01
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    
    @Autowired
    private BookMapper bookMapper;
    
    public boolean save(Book entity) {
        entity.setCreateTime(LocalDateTime.now());
        return super.save(entity);
    }
}
