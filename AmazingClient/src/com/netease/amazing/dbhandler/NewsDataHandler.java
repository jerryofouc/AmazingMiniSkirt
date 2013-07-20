package com.netease.amazing.dbhandler;

import java.util.List;

import com.netease.amazing.pojo.News;

public class NewsDataHandler {
	
	
	/**
	 * 初始化动态页面的时候，获取最新的newsCount条动态，按照时间逆序保存在List中
	 * @param newsCount 通知条数
	 * @return 获得最新的newsCount条通知
	 */
	public List<News> getInitNews(int newsCount) {
		return null;
	}
	
	/**
	 * 
	 * @param bottomNewsId 当前
	 * @param newsCount 通知条数
	 * @return 返回比id为bottomNewsId更早发布的通知，
	 * 		        返回的通知条数为newsCount，如果更早发布的通知不足newsCount条，则返回所有早发布的通知
	 */
	public List<News> getNewsByUpRefresh(int bottomNewsId,int newsCount) {
		return null;
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return 返回比topNewsId晚发布(即新发布)的所有通知
	 */
	public List<News> getNews(int topNewsId) {
		return null;
	}
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return 返回比topNewsId晚发布(即新发布)的newsCount条通知，存在以下两种情况:
	 *      case 1:当服务器中比topNewsId晚发布的信息条数大于newsCount条时，返回服务器中最新的newsCount条数据，并且按时间逆序保存在List中
	 *      case 2:当服务器中比topNewsId晚发布的信息条数(假设为n条)小于newsCount条时，则只需要返回这n条数据，并且按时间逆序保存在List中
	 */
	public List<News> getNewsByDownRefresh(int topNewsId, int newsCount) {
		return null;
	}
}
