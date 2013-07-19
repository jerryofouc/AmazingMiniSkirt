package com.netease.amazing.dbhandler;

import java.util.ArrayList;

import com.netease.amazing.pojo.Notice;
import com.netease.amazing.util.NoticeDBSimulateHandler;

public class NoticeDataHandler {
	
	/**
	 * 
	 * @param noticeCount 通知条数
	 * @return 获得最新的noticeCount条通知
	 */
	public ArrayList<Notice> getInitNotice(int noticeCount) {
		return NoticeDBSimulateHandler.getInstance().getInitNotice(noticeCount);
	}
	
	/**
	 * 
	 * @param bottomNoticeId 当前
	 * @param noticeCount 通知条数
	 * @return 返回比id为bottomNoticeId晚发布的通知，通知条数为noticecount
	 */
	public ArrayList<Notice> getNotice(int bottomNoticeId,int noticeCount) {
		return NoticeDBSimulateHandler.getInstance().getNotice(bottomNoticeId,noticeCount);
	}
	
	/**
	 * 
	 * @param topNoticeId 
	 * @return 返回比topNoticeId早发布的所有通知
	 */
	public ArrayList<Notice> getNotice(int topNoticeId) {
		return NoticeDBSimulateHandler.getInstance().getTopNotice(topNoticeId);
	}
}
