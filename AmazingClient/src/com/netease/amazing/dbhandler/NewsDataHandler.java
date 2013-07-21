package com.netease.amazing.dbhandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.netease.amazing.pojo.Contact;
import com.netease.amazing.pojo.News;
import com.netease.amazing.pojo.NewsComment;
import com.netease.amazing.pojo.NewsGrowthLog;
import com.netease.amazing.sdk.client.GrowthLogClient;
import com.netease.amazing.sdk.client.NewsRestClient;
import com.netease.amazing.sdk.dto.NewsCommentsDTO;
import com.netease.amazing.sdk.dto.NewsCommentsDTO.CommentType;
import com.netease.amazing.sdk.dto.NewsDTO.TweetType;
import com.netease.amazing.sdk.dto.NewsDTO;
import com.netease.amazing.sdk.dto.NewsGrowthLogDTO;
/**
 * Updated by Huang Xiao Jun 2013.7.20
 * @author 
 *
 */
public class NewsDataHandler {
	private static final String BASE_URL = "http://10.240.34.42:8080/server";
	public static final long USER_ID = 1;
	private static final String USER_NAME = "xukai";
	private static final String PASSWORD = "123456";
	
	/**
	 * 初始化动态页面的时候，获取最新的newsCount条动态，按照时间逆序保存在List中
	 * @param newsCount 动态条数
	 * @return 获得最新的newsCount条动态
	 */
	public static List<News> getInitNews(int newsCount) {
		List<News> newsList = new ArrayList<News>();
		NewsRestClient newsClient = new NewsRestClient(BASE_URL,USER_NAME,PASSWORD);
		List<NewsDTO> newsListTemp = null;
		try {
			newsListTemp = newsClient.getLatestNews(newsCount);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NewsDTO news:newsListTemp){
			News newsItem = convertNewsDTOToNews(news);
			newsList.add(newsItem);
		}
		return newsList;
	}
	
	/**
	 * 
	 * @param bottomNewsId 当前
	 * @param newsCount 动态条数
	 * @return 返回比id为bottomNewsId更早发布的动态，
	 * 		        返回的动态条数为newsCount，如果更早发布的动态不足newsCount条，则返回所有早发布的动态
	 */
	public static List<News> getNewsByUpRefresh(long bottomNewsId,int newsCount) {		
		List<News> newsList = new ArrayList<News>();
		NewsRestClient newsClient = new NewsRestClient(BASE_URL,USER_NAME,PASSWORD);
		List<NewsDTO> newsListTemp = null;
		try {
			newsListTemp = newsClient.getNewsByUpRefresh(bottomNewsId, newsCount);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NewsDTO news:newsListTemp){
			News newsItem = convertNewsDTOToNews(news);
			newsList.add(newsItem);
		}
		return newsList;
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return 返回比topNewsId晚发布(即新发布)的所有动态
	 */
	public static List<News> getNews(long topNewsId) {
		List<News> newsList = new ArrayList<News>();
		NewsRestClient newsClient = new NewsRestClient(BASE_URL,USER_NAME,PASSWORD);
		List<NewsDTO> newsListTemp = null;
		try {
			newsListTemp = newsClient.getNews(topNewsId);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NewsDTO news:newsListTemp){
			News newsItem = convertNewsDTOToNews(news);
			newsList.add(newsItem);
		}
		return newsList;
	}
	private static News convertNewsDTOToNews(NewsDTO news){
		News newsItem = new News();
		newsItem.setNewPublisherFrom(news.getNewPublisherFrom());
		newsItem.setNewsContent(news.getNewsContent());
		newsItem.setNewsCurrentUserLike(news.isNewsCurrentUserLike());
		newsItem.setNewsCurrentUserTakeDown(news.isNewsCurrentUserTakeDown());
		newsItem.setNewsId(news.getNewsId());
		newsItem.setNewsPublishDate(news.getNewsPublishDate());
		newsItem.setNewsPublisherName(news.getNewPublisherName());
		newsItem.setNewsPublisherRelationship(news.getNewsPublisherRelationship());
		newsItem.setNewspublisherUserId(news.getNewsPublisherId());
		newsItem.setNewsType(News.NEWS_WITH_NOTHING);
		return newsItem;
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
		List<NewsComment> newsList = new ArrayList<NewsComment>();
		List<NewsCommentsDTO>  commentList = null;
		NewsRestClient newsClient = new NewsRestClient(BASE_URL,USER_NAME,PASSWORD);
		try {
			commentList = newsClient.getNewsCommentToNewsIndexByNewsId(newsId, newsCommentCount);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NewsCommentsDTO comment:commentList){
			NewsComment newsComment = new NewsComment();
			newsComment.setNewsComment(comment.getNewsComment());
			newsComment.setNewsCommentId(comment.getNewsCommentId());
			newsComment.setNewsCommentPublisherId(comment.getNewsCommentPublisherId());
			newsComment.setNewsCommentTo(comment.getNewsCommentTo());
			if(comment.getNewsCommentType() == CommentType.LIKE){
				newsComment.setNewsCommentType(NewsComment.NEWS_COMMENT_TYPE_LIKE);
			}else if(comment.getNewsCommentType() == CommentType.INCLUDE){
				newsComment.setNewsCommentType(NewsComment.NEWS_COMMENT_TYPE_TAKE_DOWN);
			}else if(comment.getNewsCommentType() == CommentType.NORMAL){
				newsComment.setNewsCommentType(NewsComment.NEWS_COMMENT_TYPE_COMMON);
			}
			newsComment.setNewsCommmentPublisherName(comment.getNewsCommmentPublisherName());
			newsComment.setNewsId(comment.getNewsId());
			newsList.add(newsComment);
		}
		return newsList;
	}
	
	/**
	 * 当前登录用户喜欢ID为newsId的动态操作，写入评论表中，评论类型为NewsComment.NEWS_COMMENT_LIKE_TYPE
	 * @param newsId
	 * @return 如果操作成功返回true
	 */
	public static boolean setLikeNews(long newsId){
		NewsRestClient newsClient = new NewsRestClient(BASE_URL,USER_NAME,PASSWORD);
		try {
			return newsClient.setLikeNews(newsId);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 当前登录用户收录ID为newsId的动态操作，写入评论表中，评论类型为NewsComment.NEWS_COMMENT_TAKE_DOWN_TYPE
	 * @param newsId
	 * @return 如果操作成功返回true
	 */
	public static boolean setTakeDownNews(long newsId){
		NewsRestClient newsClient = new NewsRestClient(BASE_URL,USER_NAME,PASSWORD);
		try {
			return newsClient.includeNews(newsId);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static NewsGrowthLog convertNewsGrowthLogDTOToNewsGrowthLog(NewsGrowthLogDTO newsLog){
		NewsGrowthLog news = new NewsGrowthLog();
		news.setNewPublisherFrom(newsLog.getNewPublisherFrom());
		news.setNewsContent(newsLog.getNewsContent());
		news.setNewsGrowthLogOwnerUserName(newsLog.getNewsGrowthLogOwnerUserName());
		news.setNewsGrowthLogType(newsLog.getNewsGrowthLogType());
		news.setNewsId(newsLog.getNewsId());
		news.setNewsPublishDate(newsLog.getNewsPublishDate());
		news.setNewsPublisherName(newsLog.getNewsPublisherName());
		news.setNewsTakeDownUserName(newsLog.getNewsTakeDownUserName());
		if(newsLog.getNewsType() == TweetType.TEXT){
			news.setNewsType(NewsGrowthLog.NEWS_WITH_NOTHING);
		}else if(newsLog.getNewsType() == TweetType.WITH_PICTURE){
			news.setNewsType(NewsGrowthLog.NEWS_WITH_IMAGE);
		}else if(newsLog.getNewsType() == TweetType.WITH_VOICE){
			news.setNewsType(NewsGrowthLog.NEWS_WITH_VOICE);
		}
		news.setUserClass(newsLog.getUserClass());
		news.setUserJoinInClassDays(newsLog.getUserJoinInClassDays());
		
		return news;
	}
	/**
	 * 初始化成长日志主页的时候，根据用户名，获取响应最新的count条成长记录(原创或收录的动态)，按照时间逆序保存在List中
	 * @param userId 
	 * @param count
	 * @return 根据用户名，获得最新的count条成长记录(原创和收录的动态)
	 */
	public static List<NewsGrowthLog> getInitNewsGrowthLog(long userId,int count){
		List<NewsGrowthLog> newsList = new ArrayList<NewsGrowthLog>();
		List<NewsGrowthLogDTO>  commentList = null;
		GrowthLogClient newsClient = new GrowthLogClient(BASE_URL,USER_NAME,PASSWORD);
		try {
			commentList = newsClient.getInitNewsGrowthLog(userId, count);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NewsGrowthLogDTO newsGrowthLog:commentList){
			newsList.add(convertNewsGrowthLogDTOToNewsGrowthLog(newsGrowthLog));
		}
		return newsList;
	}
	
	/**
	 * @param userName
	 * @param bottomNewsId 当前
	 * @param count 动态条数
	 * @return 根据用户名，返回比id为bottomNewsId更早发布的成长记录(当前用户原创和收录的动态)，
	 * 		        返回的动态条数为count，如果更早发布的动态不足count条，则返回所有早发布的动态
	 */
	public static List<NewsGrowthLog> getNewsGrowthLogByUpRefresh(long userId, long bottomNewsId,int count) {
		return null;
	}
	
	/**
	 * @param userName
	 * @param topNewsId 
	 * @return 根据用户名， 返回比topNewsId晚发布(即新发布)的所有成长记录(当前用户原创和收录的动态)
	 */
	public static List<NewsGrowthLog> getNewsGrowthLog(long userId,long topNewsId) {
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
	public static List<NewsGrowthLog> getNewsGrowthLogByDownRefresh(long userId, long topNewsId, int count) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNewsGrowthLog(userId,topNewsId);
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
	
	/**
	 * 获取用户名为userName的用户的联系信息
	 * @param userName
	 * @return
	 */
	public static Contact getNewsPublisherContact(String userName){
		return null;
	}
}
