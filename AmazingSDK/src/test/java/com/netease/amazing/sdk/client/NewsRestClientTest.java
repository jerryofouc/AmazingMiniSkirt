package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.netease.amazing.sdk.dto.NewsDTO;

public class NewsRestClientTest extends BaseTest{
	@Test
	public void getLatestNewsTest() throws ClientProtocolException, IOException, URISyntaxException{
		NewsRestClient newsClient = new NewsRestClient(this.BASE_URL,this.USER_NAME,this.PASSWORD);
		List<NewsDTO> news = newsClient.getLatestNews(5);
		for(NewsDTO n : news){
			System.out.println(n.getNewsContent());
		}
	}
}
