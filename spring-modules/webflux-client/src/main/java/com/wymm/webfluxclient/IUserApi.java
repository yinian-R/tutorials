package com.wymm.webfluxclient;

import com.wymm.core.ApiServer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
