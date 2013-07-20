package com.netease.amazing.sdk.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	
	@Test
	public void getRangeDwonNoticeTest() throws ClientProtocolException, IOException, URISyntaxException{
		NoticeRestClient noticeRestClient = new NoticeRestClient(BASE_URL,USER_NAME, PASSWORD);
		Gson gson = new Gson();
		System.out.println(gson.toJson(noticeRestClient.getDownRangeNotice(5, 5)));
		for(NoticeDTO n :noticeRestClient.getDownRangeNotice(5, 5)){
			System.out.println(n.getId());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(df.format(n.getNoticeDate()));
		}
	}
	
	
	@Test
	public void getRangeUpNoticeTest() throws ClientProtocolException, IOException, URISyntaxException{
		NoticeRestClient noticeRestClient = new NoticeRestClient(BASE_URL,USER_NAME, PASSWORD);
		Gson gson = new Gson();
		System.out.println(gson.toJson(noticeRestClient.getUpRangeNotice(1)));
		for(NoticeDTO n :noticeRestClient.getUpRangeNotice(1)){
			System.out.println(n.getId());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(df.format(n.getNoticeDate()));
		}
	}
	
	
	@Test 
	public void createParentToTeacherNoticeTest() throws ClientProtocolException, IOException{
		NoticeDTO noticeDTO = new NoticeDTO();
		noticeDTO.setTittle("照顾小孩");
		noticeDTO.setContent("放暑假啦");
		noticeDTO.setFeedBack("是");
		Long r1 = new Long(2);
		Long r2 = new Long(3);
		List<Long> recievers = new ArrayList<Long>();
		recievers.add(r1);
		recievers.add(r2);
		noticeDTO.setRecieveObjsIDs(recievers);
		NoticeRestClient noticeRestClient = new NoticeRestClient(BASE_URL,USER_NAME, PASSWORD);
		noticeRestClient.sendNewNotice(noticeDTO);
	}
	
	@Test 
	public void createTeacherToParentNoticeTest() throws ClientProtocolException, IOException{
		NoticeDTO noticeDTO = new NoticeDTO();
		noticeDTO.setTittle("南华幼儿园通知");
		noticeDTO.setContent("放暑假啦");
		noticeDTO.setFeedBack("是");
		Long r1 = new Long(1);
		List<Long> recievers = new ArrayList<Long>();
		recievers.add(r1);
		noticeDTO.setRecieveObjsIDs(recievers);
		NoticeRestClient noticeRestClient = new NoticeRestClient(BASE_URL,"zhangxiaojie", "123456");
		noticeRestClient.sendNewNotice(noticeDTO);
	}
	
	@Test
	public void deleteNotification() throws ClientProtocolException, IOException{
		long id = 78;
		NoticeRestClient noticeRestClient = new NoticeRestClient(BASE_URL,"xukai", "123456");
		noticeRestClient.deleteNotice(id);
	}
	
}
