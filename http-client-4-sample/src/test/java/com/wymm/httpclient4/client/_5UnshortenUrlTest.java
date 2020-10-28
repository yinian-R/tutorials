package com.wymm.httpclient4.client;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.assertj.core.util.Preconditions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class _5UnshortenUrlTest {
    
    private final CloseableHttpClient client = HttpClients.createDefault();
    
    
    @Test
    public void givenShortenedOnce_whenUrlIsUnshortened_thenCorrectResult() {
        String expectedResult = "https://segmentfault.com/";
        String actualResult = expandSafe("https://sf.gg");
        assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 处理一次缩短的 URL
     *
     * @param url 短链接
     * @return 重定向后的链接
     */
    private Pair<Integer, String> expandSingleLevelSafe(String url) {
        HttpHead httpHead = null;
        try {
            httpHead = new HttpHead(url);
            CloseableHttpResponse response = client.execute(httpHead);
            
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_MOVED_PERMANENTLY && statusCode != HttpStatus.SC_MOVED_TEMPORARILY) {
                return Pair.of(statusCode, url);
            }
            
            Header[] headers = response.getHeaders(HttpHeaders.LOCATION);
            Preconditions.checkState(headers.length == 1, "http head location cannot empty");
            String newUrl = headers[0].getValue();
            return Pair.of(statusCode, newUrl);
        } catch (IOException e) {
            return Pair.of(null, url);
        } finally {
            if (httpHead != null) {
                httpHead.releaseConnection();
            }
        }
    }
    
    /**
     * 处理多次缩短的 URL
     *
     * @param url 短链接
     * @return 原链接
     */
    public String expandSafe(String url) {
        List<String> alreadyVisited = new ArrayList<>();
        
        String originalUrl = url;
        String newUrl = expandSingleLevelSafe(url).getRight();
        alreadyVisited.add(originalUrl);
        alreadyVisited.add(newUrl);
        
        while (!newUrl.equals(originalUrl)) {
            Pair<Integer, String> statusAndUrl = expandSingleLevelSafe(url);
            originalUrl = newUrl;
            newUrl = statusAndUrl.getRight();
            
            int statusCode = statusAndUrl.getLeft();
            
            boolean isRedirect = statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY;
            if (isRedirect && alreadyVisited.contains(newUrl)) {
                throw new IllegalStateException("likely a redirect loop");
            }
        }
        return newUrl;
    }
}
