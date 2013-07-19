package com.netease.amazing.dbhandler;

import java.util.ArrayList;

import com.netease.amazing.pojo.Notice;
import com.netease.amazing.util.NoticeDBSimulateHandler;

public class NoticeDataHandler {
	
	/**
	 * 
	 * @param noticeCount ֪ͨ����
	 * @return ������µ�noticeCount��֪ͨ
	 */
	public ArrayList<Notice> getInitNotice(int noticeCount) {
		return NoticeDBSimulateHandler.getInstance().getInitNotice(noticeCount);
	}
	
	/**
	 * 
	 * @param bottomNoticeId ��ǰ
	 * @param noticeCount ֪ͨ����
	 * @return ���ر�idΪbottomNoticeId������֪ͨ��֪ͨ����Ϊnoticecount
	 */
	public ArrayList<Notice> getNotice(int bottomNoticeId,int noticeCount) {
		return NoticeDBSimulateHandler.getInstance().getNotice(bottomNoticeId,noticeCount);
	}
	
	/**
	 * 
	 * @param topNoticeId 
	 * @return ���ر�topNoticeId�緢��������֪ͨ
	 */
	public ArrayList<Notice> getNotice(int topNoticeId) {
		return NoticeDBSimulateHandler.getInstance().getTopNotice(topNoticeId);
	}
}
