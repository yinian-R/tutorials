package com.wymm.elasticsearch.core;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class CustomRestClientBuilder {
	public static RestHighLevelClient restClient() {
		return new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
	}
}
