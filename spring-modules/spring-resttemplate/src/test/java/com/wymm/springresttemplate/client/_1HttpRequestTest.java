package com.wymm.springresttemplate.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wymm.springresttemplate.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static com.wymm.springresttemplate.client.HttpProperties.URL;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
@SpringBootTest
class _1HttpRequestTest {
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * GET 请求
     */
    @Test
    public void whenSendGetForRequestEntity_thenStatusOk() throws IOException {
        final ResponseEntity<Book> response = restTemplate.getForEntity(URL + "/books/1", Book.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    /**
     * POST
     */
    @Test
    public void givenBookService_whenPostForObject_thenCreatedObjectIsReturned() {
        final HttpEntity<Book> request = new HttpEntity<>(new Book("java"));
        final Book foo = restTemplate.postForObject(URL + "/books", request, Book.class);
        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("java"));
    }
    
    /**
     * 使用 ParameterizedTypeReference 处理泛型
     */
    @Test
    void usingParameterizedTypeReference() {
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                URL + "/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                });
        
        List<Book> result = response.getBody();
        result.forEach(book -> log.info(book.toString()));
    }
    
}
