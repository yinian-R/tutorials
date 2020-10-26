package com.wymm.httpclient4.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class TestController {
    
    @GetMapping
    public void home() {
    }
    
    @GetMapping("/timeout5")
    public void timeout5() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
    }
    
    @PostMapping("/form")
    public void formPost(@RequestParam Map<String, Object> params) {
        log.info(params.toString());
    }
    
    @PostMapping("/auth")
    public void authPost(@RequestParam Map<String, Object> params) {
        log.info(params.toString());
    }
    
    @PostMapping("/json")
    public void jsonPost(@RequestBody Map<String, Object> params) {
        log.info(params.toString());
    }
    
    @PostMapping("/multipart")
    public void multipartPost(@RequestParam Map<String, Object> params, @RequestParam("file") MultipartFile file) throws IOException {
        log.info(params.toString());
//        InputStream inputStream = file.getInputStream();
        log.info(file.getName());
    }
}
