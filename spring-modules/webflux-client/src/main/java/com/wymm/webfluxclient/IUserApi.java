package com.wymm.webfluxclient;

import com.wymm.core.ApiServer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 用户信息请求接口
 */
@ApiServer("http://localhost:8080/users")
public interface IUserApi {
    
    @GetMapping("/")
    Flux<User> getAllUsers();
    
    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);
    
    @DeleteMapping("/{id}")
    Mono<Void> deleteUser(@PathVariable("id") String id);
    
    @PostMapping("/")
    Mono<User> createUser(@RequestBody Mono<User> user);
}
