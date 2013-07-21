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
	 * ��ʼ����̬ҳ���ʱ�򣬻�ȡ���µ�newsCount����̬������ʱ�����򱣴���List��
	 * @param newsCount ��̬����
	 * @return ������µ�newsCount����̬
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
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ��̬����
	 * @return ���ر�idΪbottomNewsId���緢���Ķ�̬��
	 * 		        ���صĶ�̬����ΪnewsCount��������緢���Ķ�̬����newsCount�����򷵻������緢���Ķ�̬
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
	 * @return ���ر�topNewsId����(���·���)�����ж�̬
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
	 * @return ���ر�topNewsId����(���·���)��newsCount����̬�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������newsCount��ʱ�����ط����������µ�newsCount�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��newsCount��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
	 */
	public static List<News> getNewsByDownRefresh(long topNewsId, int newsCount) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNews(topNewsId);
	}
	
	/**
	 * 
	 * @param newsId ��ǰ�Ķ�̬ID
	 * @param newsCommentCount Ҫ��ȡ����������
	 * @return ������Ըö�̬�����µ�ǰ newsCommentCount������
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
	 * ��ǰ��¼�û�ϲ��IDΪnewsId�Ķ�̬������д�����۱��У���������ΪNewsComment.NEWS_COMMENT_LIKE_TYPE
	 * @param newsId
	 * @return ��������ɹ�����true
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
	 * ��ǰ��¼�û���¼IDΪnewsId�Ķ�̬������д�����۱��У���������ΪNewsComment.NEWS_COMMENT_TAKE_DOWN_TYPE
	 * @param newsId
	 * @return ��������ɹ�����true
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
	 * ��ʼ���ɳ���־��ҳ��ʱ�򣬸����û�������ȡ��Ӧ���µ�count���ɳ���¼(ԭ������¼�Ķ�̬)������ʱ�����򱣴���List��
	 * @param userId 
	 * @param count
	 * @return �����û�����������µ�count���ɳ���¼(ԭ������¼�Ķ�̬)
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
	 * @param bottomNewsId ��ǰ
	 * @param count ��̬����
	 * @return �����û��������ر�idΪbottomNewsId���緢���ĳɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)��
	 * 		        ���صĶ�̬����Ϊcount��������緢���Ķ�̬����count�����򷵻������緢���Ķ�̬
	 */
	public static List<NewsGrowthLog> getNewsGrowthLogByUpRefresh(long userId, long bottomNewsId,int count) {
		return null;
	}
	
	/**
	 * @param userName
	 * @param topNewsId 
	 * @return �����û����� ���ر�topNewsId����(���·���)�����гɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)
	 */
	public static List<NewsGrowthLog> getNewsGrowthLog(long userId,long topNewsId) {
		return null;
	}
	
	/**
	 * @param userName
	 * @param topNewsId
	 * @param newsCount
	 * @return �����û��������ر�topNewsId����(���·���)��count���ɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������count��ʱ�����ط����������µ�count�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��count��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
	 */
	public static List<NewsGrowthLog> getNewsGrowthLogByDownRefresh(long userId, long topNewsId, int count) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNewsGrowthLog(userId,topNewsId);
	}
	
	/**
	 * ��ǰ��¼�û������¶�̬���ͻ���ֻ���� ��̬�����ֺ͸���(������ͼ��)
	 * �������˸���д�붯̬�����ʱ����������������Ϣ����Ӧ���ݱ��У��ο�News.java���ֶ�
	 * 
	 * @param newsContent ��̬����������
	 * @param attachment ¼�� �� ͼƬ
	 * @param newsType �ο�News.java�й��ڶ�̬���͵ķ���
	 * @return �Ƿ�����ɹ�
	 */
	public static boolean addNews(String newsContent, byte[] attachment, int newsType){
		return false;
	}
	
	/**
	 * ��ǰ��¼�û�ɾ�������ɳ���־ �������������
	 *   case 1:�������ɳ���־�ǵ�¼�û���ԭ����̬ʱ��ֱ�������ݿ���ɾ��������̬��Ϣ
	 *   case 2:�������ɳ���־�ǵ�¼�û���¼�Ķ�̬ʱ�������ݿ��б���û�������¼�ö�̬
	 * @param newsId
	 * @return
	 */
	public static boolean deleteNewsGrowthLogById(long newsId){
		return false;
	}
	
	/**
	 * ��ȡ�û���ΪuserName���û�����ϵ��Ϣ
	 * @param userName
	 * @return
	 */
	public static Contact getNewsPublisherContact(String userName){
		return null;
	}
}
