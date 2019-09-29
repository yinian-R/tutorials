package com.wymm.webfluxclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @Autowired
    IUserApi userApi;
    
    @GetMapping("/")
    public void test() {
    
        //userApi.getAllUsers();
        //userApi.getUserById("11111");
        //userApi.deleteUser("22222");
        //userApi.createUser(Mono.just(User.builder().name("asd").age(23).build()));
    
        //Flux<User> users = userApi.getAllUsers();
        //users.subscribe(System.out::println);
    
        userApi.getUserById("5d4081dc79c4b340b0348cf9")
                .subscribe(str -> System.out.println("getUserById:" + str));
        userApi.deleteUser("5d4081dc79c4b340b0348cf9")
                .subscribe();
    }
}
