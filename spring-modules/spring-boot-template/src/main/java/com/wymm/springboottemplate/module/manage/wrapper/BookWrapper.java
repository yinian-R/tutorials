package com.wymm.springboottemplate.module.manage.wrapper;

import com.wymm.springboottemplate.module.manage.entity.dto.BookDTO;
import com.wymm.springboottemplate.module.manage.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface BookWrapper {
    
    BookDTO bookToBookDTO(Book source);
    
}
