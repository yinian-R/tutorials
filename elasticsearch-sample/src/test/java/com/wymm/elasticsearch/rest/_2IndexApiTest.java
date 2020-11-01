package com.wymm.elasticsearch.rest;

import com.wymm.elasticsearch.rest.core.CustomRestClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 没有索引，则创建索引并添加数据。有索引，则添加数据
 */
@Slf4j
public class _2IndexApiTest {
    
    private static RestHighLevelClient client;
    
    @BeforeAll
    public static void setup() {
        client = CustomRestClientBuilder.restClient();
    }
    
    /**
     * 使用 Map 作为 Source
     */
    @Test
    public void useMap_thenIndex() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1")
                .source(jsonMap);
        
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        assertEquals(indexResponse.status(), RestStatus.CREATED);
    }
    
    /**
     * 使用 XContentBuilder 作为 Source
     */
    @Test
    public void useXContentBuilder_thenIndex() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.field("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("2")
                .source(builder);
        
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        assertEquals(indexResponse.status(), RestStatus.CREATED);
    }
    
    /**
     * 使用 key-pairs 作为Source
     */
    @Test
    public void userKeyPairs_thenIndex() throws IOException {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("3")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        assertEquals(indexResponse.status(), RestStatus.CREATED);
    }
    
    /**
     * 列举可选参数
     */
    @Test
    public void useOptionalArguments() throws IOException {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("4")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch")
                // 路由
                .routing("routing")
                // 设置超时时间
                .timeout(TimeValue.timeValueSeconds(1))
                // 设置刷新策略
                .setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
                //// 设置版本
                //.version(2)
                //// 设置版本类型
                //.versionType(VersionType.EXTERNAL)
                // 操作类型
                .opType(DocWriteRequest.OpType.INDEX)
                // 设置在索引操作之前要执行管道的名称
                //.setPipeline("pipeline")
                ;
        
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        assertEquals(indexResponse.status(), RestStatus.CREATED);
    }
    
    /**
     * indexAsync() 异步执行操作
     */
    @Test
    public void asyncIndex() {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("5")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        
        Cancellable cancellable = client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            
            @Override
            public void onResponse(IndexResponse indexResponse) {
                // 执行成功时回调
                log.info("onResponse:" + indexResponse);
            }
            
            @Override
            public void onFailure(Exception e) {
                // 失败时回调
                log.info("onFailure ", e);
            }
        });
        
    }
    
    /**
     * IndexResponse 操作信息
     */
    public void indexResponseOperation() throws IOException {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("6")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        
        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            // 当文档首次被创建时
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            // 当文档被更新时
        }
        
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            // 当成功的分片不等于总分片时
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                // 处理潜在故障
            }
        }
        
    }
    
}
