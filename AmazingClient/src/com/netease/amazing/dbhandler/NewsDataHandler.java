package com.netease.amazing.dbhandler;

import java.util.List;

import com.netease.amazing.pojo.News;
import com.netease.amazing.pojo.NewsComment;
import com.netease.amazing.util.NewsDBSimulateHandler;
/**
 * Updated by Huang Xiao Jun 2013.7.20
 * @author 
 *
 */
public class NewsDataHandler {
	
	
	/**
	 * 初始化动态页面的时候，获取最新的newsCount条动态，按照时间逆序保存在List中
	 * @param newsCount 通知条数
	 * @return 获得最新的newsCount条通知
	 */
	public List<News> getInitNews(int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param bottomNewsId 当前
	 * @param newsCount 通知条数
	 * @return 返回比id为bottomNewsId更早发布的通知，
	 * 		        返回的通知条数为newsCount，如果更早发布的通知不足newsCount条，则返回所有早发布的通知
	 */
	public List<News> getNewsByUpRefresh(long bottomNewsId,int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return 返回比topNewsId晚发布(即新发布)的所有通知
	 */
	public List<News> getNews(long topNewsId) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return 返回比topNewsId晚发布(即新发布)的newsCount条通知，存在以下两种情况:
	 *      case 1:当服务器中比topNewsId晚发布的信息条数大于newsCount条时，返回服务器中最新的newsCount条数据，并且按时间逆序保存在List中
	 *      case 2:当服务器中比topNewsId晚发布的信息条数(假设为n条)小于newsCount条时，则只需要返回这n条数据，并且按时间逆序保存在List中
	 */
	public List<News> getNewsByDownRefresh(long topNewsId, int newsCount) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNews(topNewsId);
	}
	
	/**
	 * 
	 * @param newsId 当前的动态ID
	 * @param newsCommentCount 要获取的评论条数
	 * @return 返回针对该动态的最新的前 newsCommentCount条评论
	 */
	public List<NewsComment> getNewsCommentToNewsIndexByNewsId(long newsId, int newsCommentCount){
		return null;
	}
}
