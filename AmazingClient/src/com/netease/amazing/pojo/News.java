package com.netease.amazing.pojo;


/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   News���ڴ洢ÿһ����̬����������Ϣ
 */
public class News {
	public static final int NEWS_WITH_IMAGE = 1;//��ͼ��Ķ�̬
	public static final int NEWS_WITH_VOICE = 2;//�������Ķ�̬
	public static final int NEWS_WITH_NOTHING = 0;//�������ֵĶ�̬
	
	private long newsId;
	private String newspublisherUserName;//��̬��������ϵͳ�е��û���
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
	
	private boolean newsCurrentUserLike;//�ö�̬�Ƿ񱻵�ǰ��¼�û�ϲ��
	private boolean newsCurrentUserTakeDown;//�ö�̬�Ƿ񱻵�ǰ��¼�û���¼
	
	
	public String getNewspublisherUserName() {
		return newspublisherUserName;
	}
	public void setNewspublisherUserName(String newspublisherUserName) {
		this.newspublisherUserName = newspublisherUserName;
	}
	public String getNewsPublisherName() {
		return newsPublisherName;
	}
	public void setNewsPublisherName(String newsPublisherName) {
		this.newsPublisherName = newsPublisherName;
	}
	public byte[] getNewsPublisherImg() {
		return newsPublisherImg;
	}
	public void setNewsPublisherImg(byte[] newsPublisherImg) {
		this.newsPublisherImg = newsPublisherImg;
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
	public int getNewsType() {
		return newsType;
	}
	public void setNewsType(int newsType) {
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
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	public long getNewsId() {
		return newsId;
	}
}
