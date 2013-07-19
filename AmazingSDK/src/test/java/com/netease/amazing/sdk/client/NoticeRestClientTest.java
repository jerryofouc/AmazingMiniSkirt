package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.google.gson.Gson;
import com.netease.amazing.sdk.dto.NoticeDTO;

public class NoticeRestClientTest extends BaseTest{
	@Test
	public void getLatestNoticeTest() throws ClientProtocolException, IOException, URISyntaxException{
		NoticeRestClient noticeRestClient = new NoticeRestClient(BASE_URL,USER_NAME, PASSWORD);
		Gson gson = new Gson();
		System.out.println(gson.toJson(noticeRestClient.getLatestNotices(5)));
		for(NoticeDTO n :noticeRestClient.getLatestNotices(5)){
			System.out.println(n.getId());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(df.format(n.getNoticeDate()));
		}
	}
}
