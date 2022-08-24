package com.wymm.springvalidsasmple.service;

import com.wymm.springvalidsasmple.model.Book;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * @since 2022-08-22
 */
public interface ValidService {
    
    String uploadBook(List<Book> list);
    
}
