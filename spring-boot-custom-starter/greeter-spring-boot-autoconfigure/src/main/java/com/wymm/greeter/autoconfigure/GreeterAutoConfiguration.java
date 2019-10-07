package com.wymm.greeter.autoconfigure;

import com.wymm.greeter.library.GreeterTemplate;
import com.wymm.greeter.library.GreetingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.wymm.greeter.library.GreeterConfigParams.USER_NAME;

/**
 * 使用@ConditionalOnMissingBean确保没有这个Bean才创建它
 */
@Configuration
@ConditionalOnClass(GreeterTemplate.class)
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterAutoConfiguration {
    
    @Autowired
    private GreeterProperties greeterProperties;
    
    @Bean
    @ConditionalOnMissingBean
    public GreetingConfig greeterConfig() {
        String userName = greeterProperties.getUserName() == null
                ? System.getProperty("user.name")
                : greeterProperties.getUserName();
        
        // ..
        
        GreetingConfig greetingConfig = new GreetingConfig();
        greetingConfig.put(USER_NAME, userName);
        
        // ..
        
        return greetingConfig;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public GreeterTemplate greeterTemplate(GreetingConfig greetingConfig) {
        return new GreeterTemplate(greetingConfig);
    }
}
