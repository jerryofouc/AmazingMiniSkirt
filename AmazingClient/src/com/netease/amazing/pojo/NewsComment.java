package com.netease.amazing.pojo;

import java.io.Serializable;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   ��Զ�̬������
 */
public class NewsComment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int NEWS_COMMENT_COUNT_FOR_INDEX = 7;
	public static int NEWS_COMMENT_TYPE_LIKE = 1; //�����۽���ʾϲ�������̬
	public static int NEWS_COMMENT_TYPE_TAKE_DOWN = 2;//�����۽���ʾ��¼�������̬
	public static int NEWS_COMMENT_TYPE_COMMON = 0; //�����۱�ʾ�����̬Ϊ��ͨ��̬
	
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
	private int newsCommentType;//�������۵����ͣ����ϳ�����ʾ
	
	
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
	public int getNewsCommentType() {
		return newsCommentType;
	}
	public void setNewsCommentType(int newsCommentType) {
		this.newsCommentType = newsCommentType;
	}
	
	
}
