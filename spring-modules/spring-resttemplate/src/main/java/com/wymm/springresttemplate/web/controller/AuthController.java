package com.wymm.springresttemplate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class AuthController {
    
    @PostMapping("/auth")
    public void authPost(@RequestParam Map<String, Object> params) {
        log.info(params.toString());
    }
    
}
