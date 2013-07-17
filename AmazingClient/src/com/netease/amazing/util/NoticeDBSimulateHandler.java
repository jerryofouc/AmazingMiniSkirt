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
	
	public boolean getNotice(ArrayList<Notice> noticeList, int size,int lastReceivedNoticeId) {
		for(int i =0;i<size;i++) {
			noticeList.add(new Notice(true));
		}
		return true;
	}
}
