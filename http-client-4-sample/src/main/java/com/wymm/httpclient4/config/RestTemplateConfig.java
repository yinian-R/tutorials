package com.wymm.httpclient4.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;

@Configuration
public class RestTemplateConfig {
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(5))
                .requestFactory(this::requestFactory)
                .build();
    }
    
    private ClientHttpRequestFactory requestFactory() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(10)
                .disableAutomaticRetries()
                .setKeepAliveStrategy(
                        (response, context) -> Arrays.stream(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeoout") && StringUtils.isNumeric(h.getValue()))
                                .findFirst()
                                .map(h -> NumberUtils.toLong(h.getValue()))
                                .orElse(30L) * 1000
                )
                // 驱逐过期连接
                .evictExpiredConnections()
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
    
}
