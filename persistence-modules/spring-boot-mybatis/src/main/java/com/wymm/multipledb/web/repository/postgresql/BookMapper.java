package com.wymm.multipledb.web.repository.postgresql;

import com.wymm.multipledb.web.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {
    
    @Select("select * from book")
    List<Book> finds();
    
}
