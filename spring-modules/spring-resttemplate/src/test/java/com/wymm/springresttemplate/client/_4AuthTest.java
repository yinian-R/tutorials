package com.wymm.springresttemplate.client;

import com.wymm.springresttemplate.entity.Book;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.wymm.springresttemplate.client.HttpProperties.URL;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class _4AuthTest {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    @Qualifier("authRestTemplate")
    private RestTemplate authRestTemplate;
    
    /**
     * 具有授权的 POST (Http Client)
     */
    @Test
    void whenSendPostRequestWithAuthorization_usingHttpClient_thenCorrect()
            throws IOException, AuthenticationException {
        String url = URL + "/auth";
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                //.setProxy(new HttpHost("127.0.0.1", 8888))
                .build();
        HttpPost httpPost = new HttpPost(url);
        
        httpPost.setEntity(new StringEntity("test post"));
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user1", "user1Pass");
        httpPost.addHeader(new BasicScheme().authenticate(credentials, httpPost, null));
        
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        httpClient.close();
    }
    
    /**
     * 具有授权的 POST (RestTemplate)
     */
    @Test
    void whenSendPostRequestWithAuthorization_usingRestTemplate_thenCorrect() {
        String url = URL + "/books";
        final HttpHeaders headers = prepareBasicAuthHeaders();
        final HttpEntity<Book> request = new HttpEntity<>(new Book("java"), headers);
        
        final Book foo = restTemplate.postForObject(url, request, Book.class);
        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("java"));
    }
    
    private HttpHeaders prepareBasicAuthHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        final String encodedLogPass = getBase64EncodedLogPass();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedLogPass);
        return headers;
    }
    
    private String getBase64EncodedLogPass() {
        final String logPass = "user1:user1Pass";
        final byte[] authHeaderBytes = encodeBase64(logPass.getBytes(StandardCharsets.US_ASCII));
        return new String(authHeaderBytes, StandardCharsets.US_ASCII);
    }
    
    /**
     * 使用 CredentialsProvider 进行授权
     */
    @Test
    public void whenSend_usingCredentialsProvider() {
        String object = authRestTemplate.getForObject("http://127.0.0.1:8080/spring-security-mvc-digest-auth/homepage.html", String.class);
        System.out.println(object);
    }
    
}

