package com.wymm.springcloud.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

//@Component
public class PasswordFilter extends ZuulFilter {
    
    private final Logger log = LoggerFactory.getLogger(PasswordFilter.class);
    
    @Override
    public String filterType() {
        return "post";
    }
    
    @Override
    public int filterOrder() {
        return 0;
    }
    
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(">>> {} {}", request.getMethod(), request.getRequestURL().toString());
        
        String password = request.getParameter("password");
        if (StringUtils.isBlank(password)) {
            log.warn("password is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("The password cannot be empty");
            ctx.set("isSuccess", false);
            return null;
        }
        
        ctx.set("isSuccess", true);
        return null;
    }
}
