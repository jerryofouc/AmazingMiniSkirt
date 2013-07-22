package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.netease.amazing.sdk.dto.NewsGrowthLogDTO;

public class GrowthLogRestTestClient extends BaseTest{
	@Test
	public void getGrowLogTest() throws ClientProtocolException, IOException{
		GrowthLogClient growthLogClient = new GrowthLogClient(this.BASE_URL, this.USER_NAME, this.PASSWORD);
		List<NewsGrowthLogDTO> logs = growthLogClient.getInitNewsGrowthLog(1, 10);
		for(NewsGrowthLogDTO l : logs){
			System.out.println(l.getNewsId());
		}
	}
	
	@Test
	public void getNewsGrowthLogByUpRefreshTest() throws ClientProtocolException, IOException{
		GrowthLogClient growthLogClient = new GrowthLogClient(this.BASE_URL, this.USER_NAME, this.PASSWORD);
		List<NewsGrowthLogDTO> logs = growthLogClient.getNewsGrowthLogByUpRefresh(1, 5, 1);
		for(NewsGrowthLogDTO l : logs){
			System.out.println(l.getNewsId());
		}
	}
	@Test
	public void getNewsGrowthLogTest() throws ClientProtocolException, IOException{
		GrowthLogClient growthLogClient = new GrowthLogClient(this.BASE_URL, this.USER_NAME, this.PASSWORD);
		List<NewsGrowthLogDTO> logs = growthLogClient.getNewsGrowthLog(1, 4);
		for(NewsGrowthLogDTO l : logs){
			System.out.println(l.getNewsId());
		}
	}
	
	@Test
	public void getNewsGrowthLogByDownRefreshTest() throws ClientProtocolException, IOException{
		GrowthLogClient growthLogClient = new GrowthLogClient(this.BASE_URL, this.USER_NAME, this.PASSWORD);
		List<NewsGrowthLogDTO> logs = growthLogClient.getNewsGrowthLogByDownRefresh(1,1, 2);
		for(NewsGrowthLogDTO l : logs){
			System.out.println(l.getNewsId());
		}
	}
}
