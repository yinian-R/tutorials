package com.wymm.webfluxclient;

import com.wymm.core.ApiServer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost:8080/users")
public interface IUserApi {
    
    @GetMapping("/")
    Flux<User> getAllUsers();
    
    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable String id);
    
    @DeleteMapping("/{id}")
    Mono<Void> deleteUser(@PathVariable String id);
    
    @PostMapping("/")
    Mono<User> createUser(@RequestBody User user);
}
