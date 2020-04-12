package com.wymm.webflux2.handlers;

import com.wymm.webflux2.exception.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * Order设置优先级，数字越小优先级越高
 */
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        // 异常信息
        String errorMsg = toStr(throwable);
        DataBuffer dataBuffer = response.bufferFactory().wrap(errorMsg.getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private String toStr(Throwable throwable) {
        // 已知异常
        if (throwable instanceof CheckException) {
            CheckException e = (CheckException) throwable;
            return e.getFieldName() + ":错误的值 " + e.getFiledValue();
        } else {
            // 未知异常，需要打印堆栈，方便定位
            throwable.printStackTrace();
            return throwable.toString();
        }


    }
}
