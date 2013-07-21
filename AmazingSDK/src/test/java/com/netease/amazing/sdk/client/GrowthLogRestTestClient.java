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
			System.out.println(l.getNewsContent());
		}
	}
}
