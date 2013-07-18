package com.netease.amazing.dbhandler;

import java.util.ArrayList;

import com.netease.amazing.pojo.Notice;
import com.netease.amazing.util.NoticeDBSimulateHandler;

public class NoticeDataHandler {
	
	public ArrayList<Notice> getInitNotice(int noticeCount) {
		return NoticeDBSimulateHandler.getInstance().getNotice(noticeCount);
	}
	
	public ArrayList<Notice> getNotice(int bottomNoticeId,int noticeCount) {
		return NoticeDBSimulateHandler.getInstance().getNotice(noticeCount);
	}
	
	public ArrayList<Notice> getNotice(int topNoticeId) {
		return NoticeDBSimulateHandler.getInstance().getNotice(10);
	}
}
