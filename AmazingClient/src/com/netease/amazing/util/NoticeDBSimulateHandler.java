package com.netease.amazing.util;

import java.util.ArrayList;

import com.netease.amazing.pojo.Notice;

public class NoticeDBSimulateHandler {
	
	private static NoticeDBSimulateHandler handler;
	
	private NoticeDBSimulateHandler() {
		
	}
	
	public static NoticeDBSimulateHandler getInstance() {
		if(handler ==null) {
			handler = new NoticeDBSimulateHandler();
		}
		return handler;
	}
	
	public ArrayList<Notice> getNotice(int bottomNoticeId,int size) {
		ArrayList<Notice> notices = new ArrayList<Notice>();
		for(int i =bottomNoticeId;i<size;i++) {
			Notice notice = new Notice(true);
			notice.setId(i);
			notices.add(notice);
		}
		return notices;
	}
	
	public ArrayList<Notice> getTopNotice(int topNoticeId) {
		int i;
		ArrayList<Notice> notices = new ArrayList<Notice>();
		for(i=topNoticeId-10;i<topNoticeId;i++) {
			Notice notice = new Notice(true);
			notice.setId(i);
			notices.add(notice);
		}
		return notices;
	}

	public ArrayList<Notice> getInitNotice(int noticeCount) {
		int i =0;
		ArrayList<Notice> notices = new ArrayList<Notice>();
		for(i=0;i<noticeCount;i++) {
			Notice notice = new Notice(true);
			notice.setId(i);
			notices.add(notice);
		}
		return notices;
	}
}
