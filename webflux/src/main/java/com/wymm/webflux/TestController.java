package com.wymm.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/1")
    private String get1() {
        log.info("get1 start");
        String str = createStr();
        log.info("get1 end");
        return str;
    }

    @GetMapping("/2")
    private Mono<String> get2() {
        log.info("get2 start");
        // Mono.fromSupplier 是返回一个流，由于我们没有调用最终操作，所以没有阻塞 Controller
        Mono<String> just = Mono.fromSupplier(this::createStr);
        log.info("get2 end");
        return just;
    }

    //@GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //private Flux<String> get3() {
    //    Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
    //        try {
    //            TimeUnit.SECONDS.sleep(1);
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        return "flux data--" + i;
    //    }));
    //    return result;
    //}

    @GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux() {
        Flux<String> result = Flux
                .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    return "flux data--" + i;
                }));
        return result;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some thing";
    }
}
