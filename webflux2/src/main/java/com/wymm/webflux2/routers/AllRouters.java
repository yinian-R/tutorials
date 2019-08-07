package com.wymm.webflux2.routers;

import com.wymm.webflux2.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;


@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler) {

        return nest(
                // 相当于@RequestMapping("/users")
                path("/users"),
                // 相当于@GetMapping
                // 查询用户列表
                route(GET(""), handler::getAllUser)
                        // 创建用户
                        .andRoute(POST("").and(accept(APPLICATION_JSON)), handler::createUser)
                        // 删除用户
                        .andRoute(DELETE("/{id}"), handler::deleteUser)
        );
    }
}
