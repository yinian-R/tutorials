package com.wymm.springboottemplate.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wymm.springboottemplate.web.model.entity.Book;
import com.wymm.springboottemplate.web.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 图书信息 前端控制器
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private IBookService bookService;
    
    /**
     * 新增图书
     *
     * @param book 图书
     * @return true：操作成功 false：操作失败
     */
    @PostMapping
    public boolean save(@RequestBody Book book) {
        return bookService.save(book);
    }
    
    /**
     * 批量新增图书
     *
     * @param books 图书列表
     * @return true：操作成功 false：操作失败
     */
    @PostMapping("/batch")
    public boolean save(@RequestBody List<Book> books) {
        return bookService.saveBatch(books);
    }
    
    /**
     * 删除图书
     *
     * @param id 图书编码列表
     * @return true：操作成功 false：操作失败
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return bookService.removeById(id);
    }
    
    /**
     * 批量删除图书
     *
     * @param ids 图书编码列表
     * @return true：操作成功 false：操作失败
     */
    @DeleteMapping
    @RequestMapping("/batch")
    public boolean delete(List<Long> ids) {
        return bookService.removeByIds(ids);
    }
    
    /**
     * 修改图书
     *
     * @param id   图书编码
     * @param book 图书信息
     * @return true：操作成功 false：操作失败
     */
    @PutMapping
    @RequestMapping("/{id}")
    public boolean update(@PathVariable("id") Long id, Book book) {
        book.setId(id);
        return bookService.updateById(book);
    }
    
    /**
     * 查询图书列表（分页）
     *
     * @param book 查询对象
     * @param page 分页参数
     * @return 图书列表
     */
    @GetMapping
    public Page<Book> finds(Book book, Page<Book> page) {
        return bookService.page(page);
    }
    
    /**
     * 查询图书
     *
     * @param id 图书编码
     * @return 图书
     */
    @GetMapping
    @RequestMapping("/{id}")
    public Book finds(@PathVariable Long id) {
        return bookService.getById(id);
    }
}
