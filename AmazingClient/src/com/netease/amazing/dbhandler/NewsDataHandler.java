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
	 * ��ʼ����̬ҳ���ʱ�򣬻�ȡ���µ�newsCount����̬������ʱ�����򱣴���List��
	 * @param newsCount ��̬����
	 * @return ������µ�newsCount����̬
	 */
	public static List<News> getInitNews(int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ��̬����
	 * @return ���ر�idΪbottomNewsId���緢���Ķ�̬��
	 * 		        ���صĶ�̬����ΪnewsCount��������緢���Ķ�̬����newsCount�����򷵻������緢���Ķ�̬
	 */
	public static List<News> getNewsByUpRefresh(long bottomNewsId,int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return ���ر�topNewsId����(���·���)�����ж�̬
	 */
	public static List<News> getNews(long topNewsId) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
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
		return null;
	}
	
	/**
	 * ��ǰ��¼�û�ϲ��IDΪnewsId�Ķ�̬������д�����۱��У���������ΪNewsComment.NEWS_COMMENT_LIKE_TYPE
	 * @param newsId
	 * @return ��������ɹ�����true
	 */
	public static boolean setLikeNews(long newsId){
		return false;
	}
	
	/**
	 * ��ǰ��¼�û���¼IDΪnewsId�Ķ�̬������д�����۱��У���������ΪNewsComment.NEWS_COMMENT_TAKE_DOWN_TYPE
	 * @param newsId
	 * @return ��������ɹ�����true
	 */
	public static boolean setTakeDownNews(long newsId){
		return false;
	}
	/**
	 * ��ʼ���ɳ���־��ҳ��ʱ�򣬻�ȡ���µ�count����̬������ʱ�����򱣴���List��
	 * @param count
	 * @return ������µ�count���ɳ���¼(ԭ������¼�Ķ�̬)
	 */
	public static List<NewsGrowthLog> getInitNewsGrowthLog(int count){
		return null;
	}
	
	/**
	 * 
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ��̬����
	 * @return ���ر�idΪbottomNewsId���緢���ĳɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)��
	 * 		        ���صĶ�̬����Ϊcount��������緢���Ķ�̬����count�����򷵻������緢���Ķ�̬
	 */
	public static List<News> getNewsGrowthLogByUpRefresh(long bottomNewsId,int count) {
		return null;
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return ���ر�topNewsId����(���·���)�����гɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)
	 */
	public static List<News> getNewsGrowthLog(long topNewsId) {
		return null;
	}
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return ���ر�topNewsId����(���·���)��count���ɳ���¼(��ǰ�û�ԭ������¼�Ķ�̬)�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������count��ʱ�����ط����������µ�count�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��count��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
	 */
	public static List<News> getNewsGrowthLogByDownRefresh(long topNewsId, int count) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNewsGrowthLog(topNewsId);
	}
}
