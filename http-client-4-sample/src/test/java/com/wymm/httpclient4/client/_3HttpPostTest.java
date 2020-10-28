package com.wymm.httpclient4.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class _3HttpPostTest {
    
    /**
     * 基本的 POST 请求
     */
    @Test
    void whenSendPostRequestUsingHttpClient_thenCorrect()
            throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/form");
        
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", "guest"));
        params.add(new BasicNameValuePair("password", "pass"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        httpClient.close();
    }
    
    /**
     * 具有授权的 POST
     */
    @Test
    void whenSendPostRequestWithAuthorizationUsingHttpClient_thenCorrect()
            throws IOException, AuthenticationException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/auth");
        
        httpPost.setEntity(new StringEntity("test post"));
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("guest", "pass");
        httpPost.addHeader(new BasicScheme().authenticate(credentials, httpPost, null));
        
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        httpClient.close();
    }
    
    /**
     * 发送 JSON 的 POST 请求
     */
    @Test
    void whenPostJsonUsingHttpClient_thenCorrect()
            throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/json");
        
        String json = "{\"id\":1,\"name\":\"guest\"}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        
        CloseableHttpResponse response = client.execute(httpPost);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        client.close();
    }
    
    /**
     * HttpClient Fluent API进行 POST
     */
    @Test
    void whenPostFormUsingHttpClientFluentAPI_thenCorrect()
            throws IOException {
        HttpResponse response = Request.Post("http://127.0.0.1:8080/form").bodyForm(
                Form.form().add("username", "guest").add("password", "pass").build())
                .execute().returnResponse();
        
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }
    
    /**
     * 使用HttpClient上传文件
     */
    @Test
    void whenSendMultipartRequestUsingHttpClient_thenCorrect() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/multipart");
        
        File file = new File(this.getClass().getResource("/test.txt").getPath());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("username", "guest");
        builder.addTextBody("password", "pass");
        builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, "file.txt");
        
        HttpEntity build = builder.build();
        httpPost.setEntity(build);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        httpClient.close();
    }
    
    /**
     * 获取文件上传进度
     */
    @Test
    void whenGetUploadFileProgressUsingHttpClient_thenCorrect() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/multipart");
        
        File file = new File(this.getClass().getResource("/test.txt").getPath());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, "file.txt");
        HttpEntity multipart = builder.build();
        
        ProgressEntityWrapper.ProgressListener pListener =
                percentage -> {
                    log.debug(String.valueOf(percentage));
                    if (Float.compare(percentage, 100) == 0) {
                        log.debug("upload finish");
                    }
                };
        httpPost.setEntity(new ProgressEntityWrapper(multipart, pListener));
        
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        httpClient.close();
    }
    
}
