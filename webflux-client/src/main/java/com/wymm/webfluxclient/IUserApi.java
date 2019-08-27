package com.wymm.webfluxclient;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@ApiServer("http://localhost:8080/users")
public interface IUserApi {

    @GetMapping("/")
    Flux<User> getAllUser();
}
