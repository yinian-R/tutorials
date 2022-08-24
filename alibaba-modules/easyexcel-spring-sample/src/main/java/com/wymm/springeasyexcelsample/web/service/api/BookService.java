package com.wymm.springeasyexcelsample.web.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wymm.springeasyexcelsample.web.entity.Book;
import com.wymm.springeasyexcelsample.web.entity.excel.BookColorStyleExcel;
import com.wymm.springeasyexcelsample.web.entity.excel.BookExcel;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * (Book)表服务接口
 *
 * @since 2022-08-10 15:03:14
 */
public interface BookService extends IService<Book> {

    List<BookExcel> findBookExcel();
    
    void upload(List<BookExcel> data);
    
    void downloadTemplate(HttpServletResponse response) throws IOException;
    
    List<BookColorStyleExcel> findBookColorStyleExcel();
    
}

