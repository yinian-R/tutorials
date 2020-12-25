package com.wymm.httpclient4.client;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _4SSLTest {
    private final String urlOverHttps = "https://127.0.0.1:8443/hello";
    
    /**
     * 未配置 SSL 访问服务的时候异常
     */
    @Test
    void whenHttpsUrlIsConsumed_thenCorrect() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlOverHttps);
        
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        // exception
    }
    
    /**
     * 配置SSL –接受全部（HttpClient 4.4及更高版本
     * 该策略完全忽略了证书检查，这使其变得不安全，只能在有意义的地方使用。
     */
    @Test
    public final void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenOk()
            throws GeneralSecurityException {
        SSLContext sslContext = SSLContextBuilder.create()
                // 会校验证书
                // 证书请求
                // Url url =
                //.loadTrustMaterial(url, "storePassword".toCharArray())
                // 放过所有证书检验
                .loadTrustMaterial(null, ((cert, s) -> true))
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);
        
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();
        
        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();
        
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        ResponseEntity<String> response = new RestTemplate(requestFactory)
                .exchange(urlOverHttps, HttpMethod.GET, null, String.class);
        assertEquals(response.getStatusCode(), org.springframework.http.HttpStatus.OK);
    }
    
}
