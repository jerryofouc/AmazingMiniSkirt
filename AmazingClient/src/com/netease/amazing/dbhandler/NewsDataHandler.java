package com.netease.amazing.dbhandler;

import java.util.List;

import com.netease.amazing.pojo.News;

public class NewsDataHandler {
	
	
	/**
	 * ��ʼ����̬ҳ���ʱ�򣬻�ȡ���µ�newsCount����̬������ʱ�����򱣴���List��
	 * @param newsCount ֪ͨ����
	 * @return ������µ�newsCount��֪ͨ
	 */
	public List<News> getInitNews(int newsCount) {
		return null;
	}
	
	/**
	 * 
	 * @param bottomNewsId ��ǰ
	 * @param newsCount ֪ͨ����
	 * @return ���ر�idΪbottomNewsId���緢����֪ͨ��
	 * 		        ���ص�֪ͨ����ΪnewsCount��������緢����֪ͨ����newsCount�����򷵻������緢����֪ͨ
	 */
	public List<News> getNewsByUpRefresh(int bottomNewsId,int newsCount) {
		return null;
	}
	
	/**
	 * 
	 * @param topNewsId 
	 * @return ���ر�topNewsId����(���·���)������֪ͨ
	 */
	public List<News> getNews(int topNewsId) {
		return null;
	}
	
	/**
	 * 
	 * @param topNewsId
	 * @param newsCount
	 * @return ���ر�topNewsId����(���·���)��newsCount��֪ͨ�����������������:
	 *      case 1:���������б�topNewsId��������Ϣ��������newsCount��ʱ�����ط����������µ�newsCount�����ݣ����Ұ�ʱ�����򱣴���List��
	 *      case 2:���������б�topNewsId��������Ϣ����(����Ϊn��)С��newsCount��ʱ����ֻ��Ҫ������n�����ݣ����Ұ�ʱ�����򱣴���List��
	 */
	public List<News> getNewsByDownRefresh(int topNewsId, int newsCount) {
		return null;
	}
}
