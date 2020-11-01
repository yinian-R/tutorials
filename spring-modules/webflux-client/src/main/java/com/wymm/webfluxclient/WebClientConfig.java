package com.wymm.webfluxclient;

import com.wymm.core.interfaces.ProxyCreator;
import com.wymm.core.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置本工程自定义的 web client
 */
@Configuration
public class WebClientConfig {
    
    @Bean
    ProxyCreator jdkProxyCreator() {
        return new JDKProxyCreator();
    }
    
    @Bean
    FactoryBean<IUserApi> userApiFactoryBean(ProxyCreator proxyCreator) {
        return new FactoryBean<IUserApi>() {
            @Override
            public IUserApi getObject() {
                return (IUserApi) proxyCreator.createProxy(getObjectType());
            }
            
            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }
    
}
