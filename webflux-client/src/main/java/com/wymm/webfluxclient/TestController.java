package com.wymm.webfluxclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TestController {
    
    @Autowired
    IUserApi userApi;
    
    @GetMapping("/")
    public void test() {
        Flux<User> users = userApi.getAllUsers();
        users.subscribe(System.out::println);
    }
}
