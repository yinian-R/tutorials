package com.wymm.springboottemplate.module.manage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.wymm.springboottemplate.module.manage.model.entity.Book;
import com.wymm.springboottemplate.module.manage.service.BookService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 图书信息 前端控制器
 * </p>
 *
 * @author wymm
 * @since 2021-05-27
 */
@Api(value = "/books", tags = {"图书信息"})
@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    /**
     * 跳转图书页面
     */
    @GetMapping("/view")
    public ModelAndView getBooks() {
        ModelAndView view = new ModelAndView();
        view.setViewName("book");
        return view;
    }
    
    /**
     * 跳转新增图书页面
     */
    @GetMapping("/view/add")
    public ModelAndView addBook() {
        ModelAndView view = new ModelAndView();
        view.setViewName("add-book");
        return view;
    }
    
    /**
     * 新增图书
     *
     * @param book 图书
     * @return true：操作成功 false：操作失败
     */
    @ApiOperationSupport(ignoreParameters = {"id", "createTime", "updateTime"})
    @ApiOperation(value = "新增图书", notes = "新增图书", httpMethod = "POST")
    @PostMapping
    public boolean save(@RequestBody Book book) {
        return bookService.save(book);
    }
    
    /**
     * 新增图书（批量）
     *
     * @param books 图书列表
     * @return true：操作成功 false：操作失败
     */
    @ApiOperationSupport(ignoreParameters = {"id", "createTime", "updateTime"})
    @ApiOperation(value = "新增图书（批量）", notes = "批量新增图书", httpMethod = "POST")
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
    @ApiOperation(value = "删除图书", notes = "删除图书", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return bookService.removeById(id);
    }
    
    /**
     * 删除图书（批量）
     *
     * @param ids 图书编码列表
     * @return true：操作成功 false：操作失败
     */
    @ApiOperation(value = "删除图书（批量）", notes = "批量删除图书", httpMethod = "DELETE")
    @DeleteMapping("/batch")
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
    @ApiOperation(value = "修改图书", notes = "修改图书", httpMethod = "PUT")
    @PutMapping("/{id}")
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
    @ApiOperation(value = "查询图书列表（分页）", notes = "查询图书列表（分页）", httpMethod = "GET")
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
    @ApiOperation(value = "查询图书", notes = "查询图书", httpMethod = "GET")
    @GetMapping("/{id}")
    public Book finds(@PathVariable Long id) {
        return bookService.getById(id);
    }
}
