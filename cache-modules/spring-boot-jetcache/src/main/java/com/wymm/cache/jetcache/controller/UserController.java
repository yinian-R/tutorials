package com.wymm.cache.jetcache.controller;

import com.wymm.cache.jetcache.model.User;
import com.wymm.cache.jetcache.model.qo.UserQO;
import com.wymm.cache.jetcache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/findPostCondition")
    public List<User> findPostCondition(Boolean b) {
        return userService.findPostCondition(b);
    }
    
    
    @GetMapping("/findCondition")
    public List<User> findCondition(LocalDate date) {
        return userService.findCondition(date);
    }
    
    @GetMapping("/findQO")
    public List<User> findQO(UserQO qo) {
        return userService.findQO(qo);
    }
    
}
