package com.wymm.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class _1InitializationTest {
	
	/**
	 * 初始化 REST 客户端
	 * 该客户端维护一个线程池并启动了一些线程，因此在正确使用它之后关闭它释放资源
	 */
	@Test
	public void initial() throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		client.close();
	}
	
}
