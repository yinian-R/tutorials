package com.wymm.springvalidsasmple.controller;

import com.wymm.springvalidsasmple.model.Book;
import com.wymm.springvalidsasmple.service.ValidService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022-08-22
 */
@AllArgsConstructor
@RestController
public class ValidController {
    
    private final ValidService validService;
    
    @GetMapping("/checkOne")
    public String checkOne(@Valid Book book) {
        
        return null;
    }
    
    @GetMapping("/checkGroup")
    public String checkGroup() {
        List<Book> list = new ArrayList<>();
        
        Book book = new Book();
        book.setName("名称");
        //bookGroup.setAge(18);
        //bookGroup.setBookType("1");
        book.setBookTypeText("自然科学");
        
        list.add(book);
        
        return validService.uploadBook(list);
    }
    
    
}
