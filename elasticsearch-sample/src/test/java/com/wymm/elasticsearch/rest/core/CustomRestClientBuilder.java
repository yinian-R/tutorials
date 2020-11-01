package com.wymm.elasticsearch.rest.core;

import com.wymm.elasticsearch.ElasticsearchConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class CustomRestClientBuilder {
    public static RestHighLevelClient restClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(ElasticsearchConfig.HOST, 9200, "http")));
    }
}
