package com.wymm.springresttemplate.config;

import com.wymm.springresttemplate.core.LoggingInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * RestTemplate Config
 */
@Slf4j
@Configuration
public class RestTemplateConfig {
    
    @Bean
    @Primary
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        
        // 添加日志拦截器
        if (log.isDebugEnabled()) {
            if (CollectionUtils.isEmpty(restTemplate.getInterceptors())) {
                restTemplate.setInterceptors(new ArrayList<>());
            }
            restTemplate.getInterceptors().add(new LoggingInterceptor());
            // 避免日志拦截器消耗响应流，客户端程序看到空的响应主体
            restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(this.requestFactory()));
        } else {
            restTemplate.setRequestFactory(this.requestFactory());
        }
        
        return restTemplate;
    }
    
    private ClientHttpRequestFactory requestFactory() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .disableAutomaticRetries()
                .setKeepAliveStrategy(
                        (response, context) -> Arrays.stream(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeout") && StringUtils.isNumeric(h.getValue()))
                                .findFirst()
                                .map(h -> NumberUtils.toLong(h.getValue()))
                                .orElse(30L) * 1000
                )
                // 驱逐过期连接
                .evictExpiredConnections()
                //.setProxy(new HttpHost("127.0.0.1", 8888))
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(Math.toIntExact(Duration.ofSeconds(2).toMillis()));
        factory.setReadTimeout(Math.toIntExact(Duration.ofSeconds(2).toMillis()));
        return factory;
    }
    
}
