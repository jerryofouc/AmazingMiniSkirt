package com.netease.amazing.sdk.dto;


public class NewsDTO {
	public enum TweetType{
		TEXT,
		WITH_VOICE,
		WITH_PICTURE
	}
	
	private long newsId;
	private String newsPublisherName; //��̬������������ ��ǰ��¼�û���ͨѶ¼��������ϵ�˵Ķ�̬������չʾ����
	private String newsPublisherRelationship; //��̬�������뵱ǰ��¼�û��Ĺ�ϵ
	private String newsContent;//�����Ķ�̬����;
	private TweetType newsType;//������̬��Ϣ�����ͣ��ο����泣��
	/**
	 * ��̬����ʱ�䣬��ȷ�����ӣ��ڷ������˽���ʱ���жϣ��Է�������ǰʱ��Ϊ׼���ͻ��������޸�ʱ�䣬�洢��ʽ����:
	 * case 1:�����̬�ǵ��췢���ģ�ȡ����ʱ���ʽΪ������(����ע�������)14:28
	 * case 2:�����̬���ǵ��췢�����ǵ��귢���ģ�ȡ������ʽΪ��7��23��(����ע�������)14:28
	 * case 3:�����̬���ǵ��귢���ģ�ȡ����ʱ���ʽΪ��2012��5��6��(����ע�������)16:55
	 */
	private String newsPublishDate; 
	private String newPublisherFrom; //��̬���������Ե��׶�԰
	private boolean newsCurrentUserLike;//�ö�̬�Ƿ񱻵�ǰ��¼�û�ϲ��
	private boolean newsCurrentUserTakeDown;//�ö�̬�Ƿ񱻵�ǰ��¼�û���¼
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
	public String getNewsPublisherRelationship() {
		return newsPublisherRelationship;
	}
	public void setNewsPublisherRelationship(String newsPublisherRelationship) {
		this.newsPublisherRelationship = newsPublisherRelationship;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public TweetType getNewsType() {
		return newsType;
	}
	public void setNewsType(TweetType newsType) {
		this.newsType = newsType;
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
	public boolean isNewsCurrentUserLike() {
		return newsCurrentUserLike;
	}
	public void setNewsCurrentUserLike(boolean newsCurrentUserLike) {
		this.newsCurrentUserLike = newsCurrentUserLike;
	}
	public boolean isNewsCurrentUserTakeDown() {
		return newsCurrentUserTakeDown;
	}
	public void setNewsCurrentUserTakeDown(boolean newsCurrentUserTakeDown) {
		this.newsCurrentUserTakeDown = newsCurrentUserTakeDown;
	}
	
}
