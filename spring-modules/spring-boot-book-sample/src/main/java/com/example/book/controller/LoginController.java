package com.example.book.controller;

import com.example.book.config.JwtToken;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
    
    @ResponseBody
    @PostMapping("/login")
    public HttpEntity login(String name, String password, HttpSession session) {
        log.info(String.format("name=%s, passwordd=%s", name, password));
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return new ResponseEntity<>("账号密码不能为空", HttpStatus.UNAUTHORIZED);
        }
        
        
        // 认证用户
        
        //获取生成token
        Map<String, Object> map = new HashMap<>();
        //建立载荷，这些数据根据业务，自己定义。
        map.put("uid", name);
        //生成时间
        map.put("sta", new Date().getTime());
        //过期时间
        map.put("exp", new Date().getTime() + 600000);
        try {
            String token = JwtToken.creatToken(map);
            session.setAttribute(JwtToken.SESSION_TOKEN, token);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("logout")
    public HttpEntity logout(String name, String password, HttpSession session) {
        log.info("退出登录");
        session.invalidate();
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
