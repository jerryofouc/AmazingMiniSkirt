package com.netease.amazing.pojo;

import android.graphics.Bitmap;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   News���ڴ洢ÿһ����̬����������Ϣ
 */
public class News {
	public News(boolean b){
		
	}
	private int newsId;
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int id) {
		this.newsId = id;
	}
	public static final int NEWS_WITH_IMAGE = 1;//��ͼ��Ķ�̬
	public static final int NEWS_WITH_VOICE = 2;//�������Ķ�̬
	public static final int NEWS_WITH_NOTHING = 0;//�������ֵĶ�̬
	private String newsPublisherName; //��̬������������ ��ǰ��¼�û���ͨѶ¼��������ϵ�˵Ķ�̬������չʾ����
	private byte[] newsPublisherImg; //��̬������ͷ��,�洢Ϊ������
	private String newsPublisherRelationship; //��̬�������뵱ǰ��¼�û��Ĺ�ϵ
	private String newsContent;//�����Ķ�̬����;
	private int newsType;//������̬��Ϣ�����ͣ��ο����泣��
	/**
	 * ��̬����ʱ�䣬��ȷ�����ӣ��ڷ������˽���ʱ���жϣ��Է�������ǰʱ��Ϊ׼���ͻ��������޸�ʱ�䣬�洢��ʽ����:
	 * case 1:�����̬�ǵ��췢���ģ�ȡ����ʱ���ʽΪ������(����ע�������)14:28
	 * case 2:�����̬���ǵ��췢�����ǵ��귢���ģ�ȡ������ʽΪ��7��23��(����ע�������)14:28
	 * case 3:�����̬���ǵ��귢���ģ�ȡ����ʱ���ʽΪ��2012��5��6��(����ע�������)16:55
	 */
	private String newsPublishDate; 
	private String newPublisherFrom; //��̬���������Ե��׶�԰
	private byte[] newsWithImage; //��̬�е�ͼƬ��������¼�����棬ע�⶯̬������
	private byte[] newsWithVoice; //��̬�е�¼����������ͼƬ���棬ע�⶯̬������
}
