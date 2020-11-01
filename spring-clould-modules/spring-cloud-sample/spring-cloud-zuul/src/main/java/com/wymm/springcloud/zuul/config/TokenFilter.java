package com.wymm.springcloud.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {
    
    private static Logger log = LoggerFactory.getLogger(TokenFilter.class);
    
    @Override
    public String filterType() {
        return "pre"; // 可以在请求被路由之前调用
    }
    
    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }
    
    @Override
    public boolean shouldFilter() {
        return true; // 是否执行该过滤器，此处为true，说明需要过滤
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(">>> {} {}", request.getMethod(), request.getRequestURL().toString());
        
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }
        
        ctx.set("isSuccess", true);
        return null;
    }
}
