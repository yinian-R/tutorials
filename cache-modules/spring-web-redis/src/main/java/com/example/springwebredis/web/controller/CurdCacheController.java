package com.example.springwebredis.web.controller;

import com.example.springwebredis.web.model.User;
import com.example.springwebredis.web.model.qo.UserAddQO;
import com.example.springwebredis.web.service.CurdCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "spring cache 增删改查")
@RequestMapping("/curdCache")
@RestController
public class CurdCacheController {
    @Autowired
    CurdCacheService userService;
    
    @ApiModelProperty("查询所有用户")
    @GetMapping("/list")
    public List<User> findUsers() {
        return userService.findUsers();
    }
    
    @ApiModelProperty("查询用户")
    @GetMapping("/getOne")
    public User getUser(@RequestParam("id") Long id) {
        return userService.getUser(id);
    }
    
    @ApiModelProperty("创建用户")
    @PostMapping("/create")
    public User create(@RequestBody UserAddQO user) {
        return userService.addUser(user);
    }
    
    @ApiModelProperty("更新用户")
    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
    
    @ApiModelProperty("删除用户")
    @PostMapping("/delete")
    public boolean deleteUser(@RequestParam("id") Long id) {
        return userService.deleteUser(id);
    }
    
    @ApiOperation("清除所有缓存")
    @PostMapping("/clearAllCache")
    public void clearAllCache() {
        userService.clearAllCache();
    }

}
