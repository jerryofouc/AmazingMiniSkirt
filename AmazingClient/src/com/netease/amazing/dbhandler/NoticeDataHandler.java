package com.netease.amazing.dbhandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.netease.amazing.pojo.Notice;
import com.netease.amazing.sdk.client.NoticeRestClient;
import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.util.UserInfoStore;

public class NoticeDataHandler {
	
	/**
	 * 
	 * @param noticeCount 通知条数
	 * @return 获得最新的noticeCount条通知
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<Notice> getInitNotice(int noticeCount) throws ClientProtocolException, IOException, URISyntaxException {
		NoticeRestClient noticeRestClient = new NoticeRestClient(UserInfoStore.url,UserInfoStore.username,UserInfoStore.password);
		List<NoticeDTO> results =null;
		results = noticeRestClient.getLatestNotices(noticeCount);
		return convertToNotices(results);
	}
	
	private List<Notice> convertToNotices(List<NoticeDTO> results) {
		List<Notice> notices = new ArrayList<Notice>();
		Iterator<NoticeDTO> it = results.iterator();
		while(it.hasNext()) {
			NoticeDTO tempNoticeDTO = it.next();
			Notice tempNotice = new Notice();
			tempNotice.setId(tempNoticeDTO.getId());
			tempNotice.setTitle(tempNoticeDTO.getTittle());
			tempNotice.setContent(tempNoticeDTO.getContent());
			tempNotice.setNeedFeedBack(tempNoticeDTO.isNeedFeedBack());
			tempNotice.setNoticeDate(tempNoticeDTO.getNoticeDate());
			tempNotice.setAttachments(null);
			notices.add(tempNotice);
		}
		return notices;
	}

	/**
	 * 
	 * @param bottomNoticeId 当前
	 * @param noticeCount 通知条数
	 * @return 返回比id为bottomNoticeId晚发布的通知，通知条数为noticecount
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	public List<Notice> getNotice(long bottomNoticeId,int noticeCount) throws ClientProtocolException, URISyntaxException, IOException {
		
		NoticeRestClient noticeRestClient = new NoticeRestClient(UserInfoStore.url,UserInfoStore.username,UserInfoStore.password);
		List<NoticeDTO> results =null;
		
		results = noticeRestClient.getDownRangeNotice(bottomNoticeId, noticeCount);
		return convertToNotices(results);
	}
	
	/**
	 * 
	 * @param topNoticeId 
	 * @return 返回比topNoticeId早发布的所有通知
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	public List<Notice> getNotice(long topNoticeId) throws ClientProtocolException, URISyntaxException, IOException {
		
		NoticeRestClient noticeRestClient = new NoticeRestClient(UserInfoStore.url,UserInfoStore.username,UserInfoStore.password);
		List<NoticeDTO> results =null;
		results = noticeRestClient.getUpRangeNotice(topNoticeId);
		return convertToNotices(results);
	}
}
