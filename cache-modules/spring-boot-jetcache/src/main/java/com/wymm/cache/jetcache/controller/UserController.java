package com.wymm.cache.jetcache.controller;

import com.wymm.cache.jetcache.model.User;
import com.wymm.cache.jetcache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
    
    @GetMapping("/list")
    public List<User> findByBoolean(Boolean b) {
        return userService.findByBoolean(b);
    }
    
}
