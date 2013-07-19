package com.netease.amazing.dbhandler;

import java.util.ArrayList;

import com.netease.amazing.pojo.News;
import com.netease.amazing.util.NewsDBSimulateHandler;

public class NewsDataHandler {
	
	
	/**
	 * 
	 * @param newsCount 通知条数
	 * @return 获得最新的newsCount条通知
	 */
	public ArrayList<News> getInitNews(int newsCount) {
		return NewsDBSimulateHandler .getInstance().getNews(newsCount);
	}
	
	/**
	 * 
	 * @param bottomNewsId 当前
	 * @param newsCount 通知条数
	 * @return 返回比id为bottomNewsId晚发布的通知，通知条数为newsCount
	 */
	public ArrayList<News> getNews(int bottomNewsId,int newsCount) {
		return NewsDBSimulateHandler .getInstance().getNews(newsCount);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return 返回比topNewsId早发布的所有通知
	 */
	public ArrayList<News> getNews(int topNewsId) {
		return NewsDBSimulateHandler .getInstance().getNews(10);
	}
}
