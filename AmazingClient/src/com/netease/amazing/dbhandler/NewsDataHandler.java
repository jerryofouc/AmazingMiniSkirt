package com.netease.amazing.dbhandler;

import java.util.List;

import com.netease.amazing.pojo.News;
import com.netease.amazing.pojo.NewsComment;
import com.netease.amazing.pojo.NewsGrowthLog;
import com.netease.amazing.util.NewsDBSimulateHandler;
/**
 * Updated by Huang Xiao Jun 2013.7.20
 * @author 
 *
 */
public class NewsDataHandler {
	
	
	/**
	 * 初始化动态页面的时候，获取最新的newsCount条动态，按照时间逆序保存在List中
	 * @param newsCount 动态条数
	 * @return 获得最新的newsCount条动态
	 */
	public static List<News> getInitNews(int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param bottomNewsId 当前
	 * @param newsCount 动态条数
	 * @return 返回比id为bottomNewsId更早发布的动态，
	 * 		        返回的动态条数为newsCount，如果更早发布的动态不足newsCount条，则返回所有早发布的动态
	 */
	public static List<News> getNewsByUpRefresh(long bottomNewsId,int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return 返回比topNewsId晚发布(即新发布)的所有动态
	 */
	public static List<News> getNews(long topNewsId) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return 返回比topNewsId晚发布(即新发布)的newsCount条动态，存在以下两种情况:
	 *      case 1:当服务器中比topNewsId晚发布的信息条数大于newsCount条时，返回服务器中最新的newsCount条数据，并且按时间逆序保存在List中
	 *      case 2:当服务器中比topNewsId晚发布的信息条数(假设为n条)小于newsCount条时，则只需要返回这n条数据，并且按时间逆序保存在List中
	 */
	public static List<News> getNewsByDownRefresh(long topNewsId, int newsCount) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNews(topNewsId);
	}
	
	/**
	 * 
	 * @param newsId 当前的动态ID
	 * @param newsCommentCount 要获取的评论条数
	 * @return 返回针对该动态的最新的前 newsCommentCount条评论
	 */
	public static List<NewsComment> getNewsCommentToNewsIndexByNewsId(long newsId, int newsCommentCount){
		return null;
	}
	
	/**
	 * 当前登录用户喜欢ID为newsId的动态操作，写入评论表中，评论类型为NewsComment.NEWS_COMMENT_LIKE_TYPE
	 * @param newsId
	 * @return 如果操作成功返回true
	 */
	public static boolean setLikeNews(long newsId){
		return false;
	}
	
	/**
	 * 当前登录用户收录ID为newsId的动态操作，写入评论表中，评论类型为NewsComment.NEWS_COMMENT_TAKE_DOWN_TYPE
	 * @param newsId
	 * @return 如果操作成功返回true
	 */
	public static boolean setTakeDownNews(long newsId){
		return false;
	}
	/**
	 * 初始化成长日志主页的时候，根据用户名，获取响应最新的count条成长记录(原创或收录的动态)，按照时间逆序保存在List中
	 * @param userId 
	 * @param count
	 * @return 根据用户名，获得最新的count条成长记录(原创和收录的动态)
	 */
	public static List<NewsGrowthLog> getInitNewsGrowthLog(String userName,int count){
		return null;
	}
	
	/**
	 * @param userName
	 * @param bottomNewsId 当前
	 * @param count 动态条数
	 * @return 根据用户名，返回比id为bottomNewsId更早发布的成长记录(当前用户原创和收录的动态)，
	 * 		        返回的动态条数为count，如果更早发布的动态不足count条，则返回所有早发布的动态
	 */
	public static List<News> getNewsGrowthLogByUpRefresh(String userName, long bottomNewsId,int count) {
		return null;
	}
	
	/**
	 * @param userName
	 * @param topNewsId 
	 * @return 根据用户名， 返回比topNewsId晚发布(即新发布)的所有成长记录(当前用户原创和收录的动态)
	 */
	public static List<News> getNewsGrowthLog(String userName,long topNewsId) {
		return null;
	}
	
	/**
	 * @param userName
	 * @param topNewsId
	 * @param newsCount
	 * @return 根据用户名，返回比topNewsId晚发布(即新发布)的count条成长记录(当前用户原创和收录的动态)，存在以下两种情况:
	 *      case 1:当服务器中比topNewsId晚发布的信息条数大于count条时，返回服务器中最新的count条数据，并且按时间逆序保存在List中
	 *      case 2:当服务器中比topNewsId晚发布的信息条数(假设为n条)小于count条时，则只需要返回这n条数据，并且按时间逆序保存在List中
	 */
	public static List<News> getNewsGrowthLogByDownRefresh(String userName, long topNewsId, int count) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNewsGrowthLog(userName,topNewsId);
	}
	
	/**
	 * 当前登录用户发表新动态，客户端只负责传 动态的文字和附件(声音或图像)
	 * 服务器端负责写入动态的添加时间等其他各种相关信息到相应数据表中，参考News.java的字段
	 * 
	 * @param newsContent 动态的文字内容
	 * @param attachment 录音 或 图片
	 * @param newsType 参考News.java中关于动态类型的分类
	 * @return 是否操作成功
	 */
	public static boolean addNews(String newsContent, byte[] attachment, int newsType){
		return false;
	}
	
	/**
	 * 当前登录用户删除该条成长日志 ，分两种情况：
	 *   case 1:当这条成长日志是登录用户的原创动态时，直接在数据库中删除该条动态信息
	 *   case 2:当这条成长日志是登录用户收录的动态时，在数据库中标记用户不在收录该动态
	 * @param newsId
	 * @return
	 */
	public static boolean deleteNewsGrowthLogById(long newsId){
		return false;
	}
}
