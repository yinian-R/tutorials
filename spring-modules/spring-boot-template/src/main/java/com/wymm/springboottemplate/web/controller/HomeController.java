package com.wymm.springboottemplate.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "主页")
@RestController("/homes")
public class HomeController {
    
    @ApiOperation(value = "获取主页信息")
    @GetMapping
    public void home() {
    
    }
    
}
