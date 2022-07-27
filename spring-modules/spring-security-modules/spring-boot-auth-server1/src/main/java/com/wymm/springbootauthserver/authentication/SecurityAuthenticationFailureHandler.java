package com.wymm.springbootauthserver.authentication;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理登录失败时的业务逻辑
 */
public class SecurityAuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    /**
     *
     * @param request
     * @param response
     * @param exception 异常信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        //直接输出json格式的响应信息
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(exception.getMessage());
        out.flush();
        out.close();
    }
}
