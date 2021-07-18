package com.wymm.multipledb.web.service.impl;

import com.wymm.multipledb.web.entity.Book;
import com.wymm.multipledb.web.mapper.mysql.BookMapper;
import com.wymm.multipledb.web.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图书信息  服务实现类
 * </p>
 *
 * @author wymm
 * @since 2021-07-18
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
	
}
