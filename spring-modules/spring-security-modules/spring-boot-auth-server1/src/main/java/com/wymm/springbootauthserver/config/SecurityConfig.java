package com.wymm.springbootauthserver.config;

import com.wymm.springbootauthserver.authentication.SecurityAuthenticationFailureHandler;
import com.wymm.springbootauthserver.authentication.SecurityAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                //认证成功时的处理器
                .successHandler(new SecurityAuthenticationSuccessHandler())
                //认证失败时的处理器
                .failureHandler(new SecurityAuthenticationFailureHandler())
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                //未登录时的处理器
                .and()
                .build();
    }
}