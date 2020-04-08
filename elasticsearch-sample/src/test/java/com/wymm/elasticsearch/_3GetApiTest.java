package com.wymm.elasticsearch;

import com.wymm.elasticsearch.core.CustomRestClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
public class _3GetApiTest {
    private static RestHighLevelClient client;
    
    @BeforeAll
    public static void setup() {
        client = CustomRestClientBuilder.restClient();
    }
    
    /**
     * 使用 GetRequest
     */
    @Test
    public void useGetRequest() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        assertNotNull(getResponse.getSource());
    }
    
    /**
     * 禁用源检索，默认开启
     */
    @Test
    public void useFetchSourceContext() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1")
                .fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        assertNull(getResponse.getSource());
    }
    
    /**
     * 为特定字段配置源包含
     */
    @Test
    public void useFetchSourceContext_thenIncludes() throws IOException {
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        GetRequest getRequest = new GetRequest("posts", "1")
                .fetchSourceContext(new FetchSourceContext(true, includes, excludes));
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        assertNotNull(getResponse.getSource().get("message"));
        assertNotNull(getResponse.getSource().get("postDate"));
    }
    
    /**
     * 为特定字段配置源排除
     */
    @Test
    public void useFetchSourceContext_thenExcludes() throws IOException {
        String[] includes = Strings.EMPTY_ARRAY;
        String[] excludes = new String[]{"message"};
        GetRequest getRequest = new GetRequest("posts", "1")
                .fetchSourceContext(new FetchSourceContext(true, includes, excludes));
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        
        assertNull(getResponse.getSource().get("message"));
    }
    
    /**
     * 列举一些可选参数
     */
    @Test
    public void optional() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1")
                // 设置路由
                .routing("routing")
                // 设置偏好值
                .preference("preference")
                // 将实时标志设置为false，默认true
                .realtime(false)
                // 在检索文档前执行刷新，默认false
                .refresh(true)
                // 设置版本号
                .version(2)
                // 设置版本类型
                .versionType(VersionType.EXTERNAL);
        
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
    }
    
    /**
     * getAsync() 异步执行
     */
    @Test
    public void asyncGet() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");
        Cancellable cancellable = client.getAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse documentFields) {
                // 执行成功时回调
                log.info("onResponse {}", documentFields);
            }
            
            @Override
            public void onFailure(Exception e) {
                // 失败时回调
                log.error("onFailure ", e);
            }
        });
    }
    
    /**
     * 将文档检索为 String
     * 将文档检索为 Map<String, Object>
     * 将文档检索为 byte[]
     * 处理找不到文档的情况。请注意，尽管返回的响应具有404状态码，但将返回有效值GetResponse，而不是引发异常。
     * 这样的响应不包含任何源文档，并且其isExists方法返回false。
     */
    @Test
    public void getResponse() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        
        String index = getResponse.getIndex();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();
            Map<String, Object> source = getResponse.getSource();
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();
        } else {
            // do something
        }
    }
    
    /**
     * 当get的索引不存在时，响应具有404状态码，抛出 ElasticsearchException
     */
    @Test
    public void indexNotFound() throws IOException {
        GetRequest request = new GetRequest("does_not_exist", "1");
        try {
            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                // do something
            }
        }
    }
    
    /**
     * 当请求特定的文档版本，并且文档不具有这版本时，响应具有409状态码，抛出 ElasticsearchException
     */
    @Test
    public void indexNotFoundVersion() throws IOException {
        GetRequest request = new GetRequest("posts", "1").version(100);
        try {
            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                // do something
            }
        }
    }
}
