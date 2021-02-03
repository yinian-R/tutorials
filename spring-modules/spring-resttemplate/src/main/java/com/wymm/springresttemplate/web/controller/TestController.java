package com.wymm.springresttemplate.web.controller;

import com.wymm.springresttemplate.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test
 */
@RestController
public class TestController {
    
    @GetMapping("/testMsg")
    public String testMsg(String text) {
        return "text:" + text;
    }
    
}
