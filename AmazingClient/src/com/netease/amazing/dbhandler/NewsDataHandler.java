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
	 * ��ʼ����̬ҳ���ʱ�򣬻�ȡ���µ�newsCount����̬������ʱ�����򱣴���List��
	 * @param newsCount ֪ͨ����
	 * @return ������µ�newsCount��֪ͨ
	 */
	public List<News> getInitNews(int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ֪ͨ����
	 * @return ���ر�idΪbottomNewsId���緢����֪ͨ��
	 * 		        ���ص�֪ͨ����ΪnewsCount��������緢����֪ͨ����newsCount�����򷵻������緢����֪ͨ
	 */
	public List<News> getNewsByUpRefresh(long bottomNewsId,int newsCount) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return ���ر�topNewsId����(���·���)������֪ͨ
	 */
	public List<News> getNews(long topNewsId) {
		return NewsDBSimulateHandler.getInstance().getNews(5);
	}
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return ���ر�topNewsId����(���·���)��newsCount��֪ͨ�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������newsCount��ʱ�����ط����������µ�newsCount�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��newsCount��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
	 */
	public List<News> getNewsByDownRefresh(long topNewsId, int newsCount) {
		//return NewsDBSimulateHandler.getInstance().getNews(5);
		return getNews(topNewsId);
	}
	
	/**
	 * 
	 * @param newsId ��ǰ�Ķ�̬ID
	 * @param newsCommentCount Ҫ��ȡ����������
	 * @return ������Ըö�̬�����µ�ǰ newsCommentCount������
	 */
	public List<NewsComment> getNewsCommentToNewsIndexByNewsId(long newsId, int newsCommentCount){
		return null;
	}
}
