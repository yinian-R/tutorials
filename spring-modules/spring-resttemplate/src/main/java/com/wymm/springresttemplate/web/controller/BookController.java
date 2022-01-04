package com.wymm.springresttemplate.web.controller;

import com.wymm.springresttemplate.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/books")
@RestController
public class BookController {
    
    @GetMapping("/{id}")
    public Book findBook(@PathVariable("id") Integer id) {
        return Book.builder().id(id).build();
    }
    
    @GetMapping
    public List<Book> finds() {
        return IntStream.range(1, 10)
                .mapToObj(i -> Book.builder().id(i).build())
                .collect(Collectors.toList());
    }
    
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        book.setId(1);
        return book;
    }
}
