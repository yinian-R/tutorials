package com.wymm.springboothttp2client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class Http2Config {
    
    @Value("${security.key-store}")
    private Resource keyStore;
    
    @Value("${security.key-pass}")
    private String keyPass;
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(100))
                .setReadTimeout(Duration.ofMillis(500))
                .requestFactory(this::requestFactory)
                .build();
    }
    
    private ClientHttpRequestFactory requestFactory() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create()
                    // 会校验证书
                    .loadTrustMaterial(keyStore.getURL(), keyPass.toCharArray())
                    // 放过所有证书检验
                    //.loadTrustMaterial(null, ((x509Certificates, s) -> true))
                    .build();
        } catch (Exception e) {
            log.error("Exception occurred while creating SSLContext.", e);
        }
        
        CloseableHttpClient httpClient = HttpClients.custom()
                .evictIdleConnections(30, TimeUnit.SECONDS)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .disableAutomaticRetries()
                .setKeepAliveStrategy(
                        (response, content) -> Arrays.stream(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                                .filter(h -> StringUtils.equalsIgnoreCase(h.getName(), "timeoout") && StringUtils.isNumeric(h.getValue()))
                                .findFirst()
                                .map(h -> NumberUtils.toLong(h.getValue()))
                                .orElse(30L) * 1000
                )
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                // 驱逐过期连接
                .evictExpiredConnections()
                .build();
        
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
