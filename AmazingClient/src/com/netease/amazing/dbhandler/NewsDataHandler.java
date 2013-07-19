package com.netease.amazing.dbhandler;

import java.util.ArrayList;

import com.netease.amazing.pojo.News;
import com.netease.amazing.util.NewsDBSimulateHandler;

public class NewsDataHandler {
	
	
	/**
	 * 
	 * @param newsCount ֪ͨ����
	 * @return ������µ�newsCount��֪ͨ
	 */
	public ArrayList<News> getInitNews(int newsCount) {
		return NewsDBSimulateHandler .getInstance().getNews(newsCount);
	}
	
	/**
	 * 
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ֪ͨ����
	 * @return ���ر�idΪbottomNewsId������֪ͨ��֪ͨ����ΪnewsCount
	 */
	public ArrayList<News> getNews(int bottomNewsId,int newsCount) {
		return NewsDBSimulateHandler .getInstance().getNews(newsCount);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return ���ر�topNewsId�緢��������֪ͨ
	 */
	public ArrayList<News> getNews(int topNewsId) {
		return NewsDBSimulateHandler .getInstance().getNews(10);
	}
}
