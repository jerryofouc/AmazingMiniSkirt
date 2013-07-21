package com.netease.amazing.dbhandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.netease.amazing.component.MyApplication;
import com.netease.amazing.pojo.Notice;
import com.netease.amazing.sdk.client.NoticeRestClient;
import com.netease.amazing.sdk.dto.NoticeDTO;
import com.netease.amazing.util.NoticeDBSimulateHandler;

public class NoticeDataHandler {
	
	/**
	 * 
	 * @param noticeCount ֪ͨ����
	 * @return ������µ�noticeCount��֪ͨ
	 */
	public List<Notice> getInitNotice(int noticeCount) {
//		String url = "http://" + myApp.getServeIp() + ":" + myApp.getServerPort() + "/server";
//		String username = myApp.getUsername();
//		String password = myApp.getPassword();
		String url = "http://10.240.34.42:8080/server";
		String username = "xukai";
		String password = "123456";
		NoticeRestClient noticeRestClient = new NoticeRestClient(url,username,password);
		List<NoticeDTO> results =null;
		try {
			results = noticeRestClient.getLatestNotices(noticeCount);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
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
	 * @param bottomNoticeId ��ǰ
	 * @param noticeCount ֪ͨ����
	 * @return ���ر�idΪbottomNoticeId������֪ͨ��֪ͨ����Ϊnoticecount
	 */
	public List<Notice> getNotice(long bottomNoticeId,int noticeCount) {
		
//		String url = "http://" + myApp.getServeIp() + ":" + myApp.getServerPort() + "/server";
//		String username = myApp.getUsername();
//		String password = myApp.getPassword();
		String url = "http://10.240.34.42:8080/server";
		String username = "xukai";
		String password = "123456";
		NoticeRestClient noticeRestClient = new NoticeRestClient(url,username,password);
		List<NoticeDTO> results =null;
		try {
			results = noticeRestClient.getDownRangeNotice(bottomNoticeId, noticeCount);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		return convertToNotices(results);
	}
	
	/**
	 * 
	 * @param topNoticeId 
	 * @return ���ر�topNoticeId�緢��������֪ͨ
	 */
	public List<Notice> getNotice(long topNoticeId) {
//		String url = "http://" + myApp.getServeIp() + ":" + myApp.getServerPort() + "/server";
//		String username = myApp.getUsername();
//		String password = myApp.getPassword();
		String url = "http://10.240.34.42:8080/server";
		String username = "xukai";
		String password = "123456";
		NoticeRestClient noticeRestClient = new NoticeRestClient(url,username,password);
		List<NoticeDTO> results =null;
		try {
			results = noticeRestClient.getUpRangeNotice(topNoticeId);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		return convertToNotices(results);
	}
}
