package com.wymm.webflux.controller;

import com.wymm.webflux.domain.User;
import com.wymm.webflux.repository.UserRepository;
import com.wymm.webflux.utils.CheckUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * 根据年龄查找用户
     *
     * @param start 开始年龄(不包含)
     * @param end   结束年龄(不包含)
     * @return 用户列表
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable int start, @PathVariable int end) {
        return this.userRepository.findByAgeBetween(start, end);
    }

    /**
     * 根据年龄查找用户
     *
     * @param start 开始年龄
     * @param end   结束年龄
     * @return 用户列表
     */
    @GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAge(@PathVariable int start, @PathVariable int end) {
        return this.userRepository.findByAgeBetween(start, end);
    }

    /**
     * 得到20~30年龄的用户列表
     *
     * @return 20~30年龄的用户列表
     */
    @GetMapping("/old")
    public Flux<User> oldUser() {
        return this.userRepository.oldUser();
    }

    /**
     * 得到20~30年龄的用户列表
     *
     * @return 20~30年龄的用户列表
     */
    @GetMapping(value = "/stream/old", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamOldUser() {
        return this.userRepository.oldUser();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        user.setId(null);
        CheckUtils.checkName(user.getName());
        return userRepository.save(user);

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        return userRepository.findById(id)
                // 当更新数据的时候使用flatMap
                // 当不操作数据，只是转换数据的时候使用map
                .flatMap(userRepository::delete)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        CheckUtils.checkName(user.getName());
        return userRepository.findById(id)
                // flatMap 操作数据
                .flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    return this.userRepository.save(u);
                })
                // map 转换数据
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
