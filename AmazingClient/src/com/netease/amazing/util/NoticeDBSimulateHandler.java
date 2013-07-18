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
	
	public ArrayList<Notice> getNotice(int size) {
		ArrayList<Notice> notices = new ArrayList<Notice>();
		for(int i =0;i<size;i++) {
			notices.add(new Notice(true));
		}
		return notices;
	}
}
