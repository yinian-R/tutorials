package com.wymm.webflux2.handlers;

import com.wymm.webflux2.domain.User;
import com.wymm.webflux2.repository.UserRepository;
import com.wymm.webflux2.utils.CheckUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@AllArgsConstructor
@Component
public class UserHandler {

    private final UserRepository userRepository;

    /**
     * 得到所有用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(this.userRepository.findAll(), User.class);
    }

    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return user.flatMap(u -> {
            CheckUtils.checkName(u.getName());
            return ok().contentType(APPLICATION_JSON)
                    .body(this.userRepository.save(u), User.class);
        });
    }

    /**
     * 删除用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.userRepository.findById(id).flatMap(user -> this.userRepository.delete(user)
                .then(ok().build()))
                .switchIfEmpty(notFound().build());
    }

}
