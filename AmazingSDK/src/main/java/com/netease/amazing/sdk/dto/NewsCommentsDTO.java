package com.netease.amazing.sdk.dto;

public class NewsCommentsDTO {
	public enum CommentType{
		LIKE,
		INCLUDE,//��¼
		NORMAL//��ͨ
	}
	
	private long newsId; //������������idΪnewsId�Ķ�̬
	private long newsCommentId; //�������۵�ID
	private long newsCommentPublisherId;// �������۷����˵�ID���������˻ظ���������ʱ�����������ݿ�
	private String newsCommmentPublisherName; //�������۵ķ���������
	/**
	 * �������ۻظ��Ķ��󣬿���Ϊ�գ�
	 *    case 1:���Ϊ�գ���ǰ��չʾΪЧ������     ������������(newsCommentPublisher)�������̬����(newsComment)
	 *    case 2:�����Ϊ�գ���ǰ��չʾЧ������      ����������(newsCommentPublisher) �ظ� ������ �����裨newsCommentTo������Ҳ���ò���newsComment��
	 */
	private String newsCommentTo;
	private String newsComment; //�������۵�����
	private CommentType newsCommentType;//�������۵����ͣ����ϳ�����ʾ
	public long getNewsId() {
		return newsId;
	}
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	public long getNewsCommentId() {
		return newsCommentId;
	}
	public void setNewsCommentId(long newsCommentId) {
		this.newsCommentId = newsCommentId;
	}
	public long getNewsCommentPublisherId() {
		return newsCommentPublisherId;
	}
	public void setNewsCommentPublisherId(long newsCommentPublisherId) {
		this.newsCommentPublisherId = newsCommentPublisherId;
	}
	public String getNewsCommmentPublisherName() {
		return newsCommmentPublisherName;
	}
	public void setNewsCommmentPublisherName(String newsCommmentPublisherName) {
		this.newsCommmentPublisherName = newsCommmentPublisherName;
	}
	public String getNewsCommentTo() {
		return newsCommentTo;
	}
	public void setNewsCommentTo(String newsCommentTo) {
		this.newsCommentTo = newsCommentTo;
	}
	public String getNewsComment() {
		return newsComment;
	}
	public void setNewsComment(String newsComment) {
		this.newsComment = newsComment;
	}
	public CommentType getNewsCommentType() {
		return newsCommentType;
	}
	public void setNewsCommentType(CommentType newsCommentType) {
		this.newsCommentType = newsCommentType;
	}
}
