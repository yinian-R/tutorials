package com.example.book.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
public class LoginHandleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("拦截器" + request.getServletPath());

        HttpSession session = request.getSession();
        String token = (String) session.getAttribute(JwtToken.SESSION_TOKEN);

        log.info("token = "+ token);
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        Map<String, Object> validMap = JwtToken.valid(token);
        int i = (int) validMap.get("Result");
        if (i == 2) {
            // token已经过期
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
