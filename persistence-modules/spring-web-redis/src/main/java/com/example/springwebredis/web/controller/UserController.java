package com.example.springwebredis.web.controller;

import com.example.springwebredis.web.model.User;
import com.example.springwebredis.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping("/users")
    public HttpEntity findUsers() {
        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
    }
    
    @GetMapping("/users/{id}")
    public HttpEntity findUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }
    
    @DeleteMapping("/users/{id}")
    public HttpEntity deleteUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
    
    @PostMapping("/users")
    public HttpEntity addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }
}
