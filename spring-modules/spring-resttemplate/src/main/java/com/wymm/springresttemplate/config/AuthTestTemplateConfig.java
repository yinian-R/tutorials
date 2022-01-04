package com.wymm.springresttemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Config
 */
@Slf4j
@Configuration
public class AuthTestTemplateConfig {
    
    @Bean
    public RestTemplate authRestTemplate() {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider())
                .useSystemProperties()
                .setProxy(new HttpHost("127.0.0.1", 8888))
                .build();
        
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(client);
        return new RestTemplate(requestFactory);
    }
    
    private CredentialsProvider provider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user1", "user1Pass");
        // 可设置授权范围
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }
    
}
