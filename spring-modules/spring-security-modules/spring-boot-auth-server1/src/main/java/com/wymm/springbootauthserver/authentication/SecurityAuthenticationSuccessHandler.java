package com.wymm.springbootauthserver.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理登录成功时的业务逻辑
 */
public class SecurityAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    /**
     * @param request
     * @param response
     * @param authentication 携带登录的用户名及角色等信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //直接输出json格式的响应信息
        Object principal = authentication.getPrincipal();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        //以json格式对外输出身份信息
        out.write(new ObjectMapper().writeValueAsString(principal));
        out.flush();
        out.close();
    }
}
