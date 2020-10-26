package com.wymm.webfluxclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @Autowired
    IUserApi userApi;
    
    @GetMapping("/")
    public void test() throws InterruptedException {
        
        //userApi.getAllUsers();
        //userApi.getUserById("11111");
        //userApi.deleteUser("22222");
        //userApi.createUser(Mono.just(User.builder().name("asd").age(23).build()));
        
        //Flux<User> users = userApi.getAllUsers();
        //users.subscribe(System.out::println);
        
        userApi.getUserById("5d4081dc79c4b340b0348cf92")
                .subscribe(str -> {
                    System.out.println("找到用户:" + str);
                }, e -> {
                    // 异常处理
                    System.err.println("找不到用户 " + e.getMessage());
                });
        
        //userApi.deleteUser("5d4081dc79c4b340b0348cf9")
        //        .subscribe();
        
        
        //User user = User.builder().name("qwewq777777").age(23).build();
        //userApi.createUser(Mono.just(user))
        //        .subscribe(value -> {
        //            System.out.println("asda:" + value);
        //        });
    }
}
