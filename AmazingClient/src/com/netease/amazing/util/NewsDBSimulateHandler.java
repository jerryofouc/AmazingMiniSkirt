package com.netease.amazing.util;

import java.util.ArrayList;

import com.netease.amazing.pojo.News;

public class NewsDBSimulateHandler {
	
	private static NewsDBSimulateHandler handler;
	
	private NewsDBSimulateHandler() {
		
	}
	
	public static NewsDBSimulateHandler getInstance() {
		if(handler ==null) {
			handler = new NewsDBSimulateHandler();
		}
		return handler;
	}
	
	public ArrayList<News> getNews(int size) {
		ArrayList<News> Newss = new ArrayList<News>();
		for(int i =0;i<size;i++) {
			Newss.add(new News());
		}
		return Newss;
	}
}
