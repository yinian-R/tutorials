package com.wymm.springboottemplate.module.manage.wrapper;

import com.wymm.springboottemplate.module.manage.model.dto.BookDTO;
import com.wymm.springboottemplate.module.manage.model.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookWrapperTest {
    
    @Autowired
    private BookWrapper bookWrapper;
    @Test
    void bookToBookDTO() {
        Book book = new Book();
        book.setId(5L);
        book.setBookName("Java");
        
        BookDTO bookDTO = bookWrapper.bookToBookDTO(book);
        
        assertEquals(bookDTO.getId(), 5L);
        assertEquals(bookDTO.getBookName(), book.getBookName());
    }
}