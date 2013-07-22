package com.netease.amazing.pojo;

import java.io.Serializable;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   NewsGrowthLog���ڴ洢�ɳ���¼(ԭ��������¼�Ķ�̬)
 */
public class NewsGrowthLog implements Serializable{
	public static final int NEWS_WITH_IMAGE = 1;//��ͼ��Ķ�̬
	public static final int NEWS_WITH_VOICE = 2;//�������Ķ�̬
	public static final int NEWS_WITH_NOTHING = 0;//�������ֵĶ�̬
	
	public static final int NEWS_GROWTH_TYPE_ORIGINAL = 0;//�����ɳ���¼���Լ������Ķ�̬
	public static final int NEWS_GROWTH_TYPE_TAKE_DOWN = 1;//�����ɳ���¼�Ǳ��˷����Ķ�̬�����ǵ�ǰ��¼�û���¼��
	
	private String newsGrowthLogOwnerUserName; //������̬����˭�ĳɳ���־(ԭ��������¼�Ķ�̬),������ϵͳ�е��û���
	private long newsId; //�Լ������Ķ�̬������¼���˷����Ķ�̬��ID
	private String newsPublisherName; //������̬�����˵�����
	private String newsContent;//�����Ķ�̬����;
	private int newsType;//������̬��Ϣ�����ͣ���ͼ�񡢴�������������
	private int newsGrowthLogType;//�����ɳ���¼����̬����ԭ����������¼
	
	private String newsTakeDownUserName;//�����Ϣ����¼�ģ���¼������Ϣ����¼������
	private String userClass;//��ǰ��¼�û���С�����ڰ༶����(ֻ��������֮һ��С�ࡢ�аࡢ���)
	private int userJoinInClassDays;// ��ǰ�ɳ���¼����(��̬)����ʱ��С���Ѽ�������༶������
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
	public long getNewsId() {
		return newsId;
	}
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	public String getNewsPublisherName() {
		return newsPublisherName;
	}
	public void setNewsPublisherName(String newsPublisherName) {
		this.newsPublisherName = newsPublisherName;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public int getNewsType() {
		return newsType;
	}
	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}
	public int getNewsGrowthLogType() {
		return newsGrowthLogType;
	}
	public void setNewsGrowthLogType(int newsGrowthLogType) {
		this.newsGrowthLogType = newsGrowthLogType;
	}
	public String getNewsTakeDownUserName() {
		return newsTakeDownUserName;
	}
	public void setNewsTakeDownUserName(String newsTakeDownUserName) {
		this.newsTakeDownUserName = newsTakeDownUserName;
	}
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	public int getUserJoinInClassDays() {
		return userJoinInClassDays;
	}
	public void setUserJoinInClassDays(int userJoinInClassDays) {
		this.userJoinInClassDays = userJoinInClassDays;
	}
	public String getNewsPublishDate() {
		return newsPublishDate;
	}
	public void setNewsPublishDate(String newsPublishDate) {
		this.newsPublishDate = newsPublishDate;
	}
	public String getNewPublisherFrom() {
		return newPublisherFrom;
	}
	public void setNewPublisherFrom(String newPublisherFrom) {
		this.newPublisherFrom = newPublisherFrom;
	}
	public byte[] getNewsWithImage() {
		return newsWithImage;
	}
	public void setNewsWithImage(byte[] newsWithImage) {
		this.newsWithImage = newsWithImage;
	}
	public byte[] getNewsWithVoice() {
		return newsWithVoice;
	}
	public void setNewsWithVoice(byte[] newsWithVoice) {
		this.newsWithVoice = newsWithVoice;
	}
	public String getNewsGrowthLogOwnerUserName() {
		return newsGrowthLogOwnerUserName;
	}
	public void setNewsGrowthLogOwnerUserName(String newsGrowthLogOwnerUserName) {
		this.newsGrowthLogOwnerUserName = newsGrowthLogOwnerUserName;
	}
}
