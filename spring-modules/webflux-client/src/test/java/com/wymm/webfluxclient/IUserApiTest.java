package com.wymm.webfluxclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserApiTest {
    
    @Autowired
    private IUserApi userApi;
    
    @Test
    public void getAllUsers() {
        Flux<User> users = userApi.getAllUsers();
    }
    
    @Test
    public void getUserById() throws InterruptedException {
        
        userApi.getUserById("5d4081dc79c4b340b0348cf92")
                .subscribe(str -> {
                    System.out.println("找到用户:" + str);
                }, e -> {
                    // 异常处理
                    System.err.println("找不到用户 " + e.getMessage());
                });
        
        TimeUnit.SECONDS.sleep(1);
    }
    
    @Test
    public void deleteUser() {
        userApi.deleteUser("5d4081dc79c4b340b0348cf9")
                .subscribe();
    }
    
    @Test
    public void createUser() {
        User user = User.builder().name("qwewq777777").age(23).build();
        userApi.createUser(Mono.just(user))
                .subscribe(value -> {
                    System.out.println("asda:" + value);
                });
    }
}