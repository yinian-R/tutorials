package com.wymm.springresttemplate.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wymm.springresttemplate.entity.Book;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class SimpleTest {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Test
    public void whenSendPostRequestWithAuthorization_usingRestTemplate_thenCorrect() {
        String url = "http://127.0.0.1:8080/all/postAll";
        final HttpHeaders headers = new HttpHeaders();
        String token = "asa token";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(token));
        JSONObject zeroTrustParam = new JSONObject();
        zeroTrustParam.put("tokenId", token.split(" ")[1]);
        zeroTrustParam.put("authType", "1");
        zeroTrustParam.put("taskId", "1");
        headers.put("zerotrust", Collections.singletonList(Base64.encode(zeroTrustParam.toJSONString())));
        
        
        final HttpEntity<Book> request = new HttpEntity<>(new Book(), headers);
        
        final String res = restTemplate.postForObject(url, request, String.class);
    }
    
    @Test
    void test2(){
        String url = "http://127.0.0.1:8080/all/postAll";
        String token = "asa token";
        
        JSONObject zeroTrustParam = new JSONObject();
        zeroTrustParam.put("tokenId", token.split(" ")[1]);
        zeroTrustParam.put("authType",  "1");
        zeroTrustParam.put("taskId", "1");
        
//        Base64.encode(zeroTrustParam.toJSONString());
        
        
        JSONObject obj = new JSONObject(4);
        obj.put("tokenId", token.split(" ")[1]);
        obj.put("taskId", "1");
        obj.put("authType", "1");
        // Base64加密
        String zeroTrust = Base64.encode(obj.toJSONString());
        
        
        
        // 构造请求头
        Map<String, String> postHeader = new HashMap<>(4);
        postHeader.put("zerotrust", zeroTrust);
        
        
        // 发送请求
        String result = post(url, null, postHeader);
        System.out.println(result);
    }
    
    protected String post(String uri, Object param, Map<String, String> header, HttpCookie... cookies) {
        HttpResponse response = null;
        try {
            response = postHttpRequest(uri, param, header, cookies);
            String respString = response.body();
            return respString;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
    
    
    protected HttpResponse postHttpRequest(String uri, Object param, Map<String, String> header,
                                           HttpCookie... cookies) {
        if (header == null) {
            header = MapUtil.newHashMap();
        }
        header.put("Content-Type", ContentType.JSON.toString());
        String url = StrUtil.startWith(uri, "http") ? uri : "getHost()" + uri;
        String body = param == null ? null
                : JSON.toJSONString(param, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat);
        
        HttpRequest request = HttpUtil.createRequest(Method.POST, url).addHeaders(header).cookie(cookies)
                .timeout(this.timeout()).charset(StandardCharsets.UTF_8);
        
        Optional.ofNullable(body).ifPresent(p -> request.body(body));
        return request.execute();
    }
    
    protected Integer timeout() {
        return 100;
    }
}
