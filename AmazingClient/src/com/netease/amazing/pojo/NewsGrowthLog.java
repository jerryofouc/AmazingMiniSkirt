package com.netease.amazing.pojo;

import java.io.Serializable;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   NewsGrowthLog用于存储成长记录(原创或者收录的动态)
 */
public class NewsGrowthLog implements Serializable{
	public static final int NEWS_WITH_IMAGE = 1;//带图像的动态
	public static final int NEWS_WITH_VOICE = 2;//带声音的动态
	public static final int NEWS_WITH_NOTHING = 0;//单纯文字的动态
	
	public static final int NEWS_GROWTH_TYPE_ORIGINAL = 0;//该条成长记录是自己发布的动态
	public static final int NEWS_GROWTH_TYPE_TAKE_DOWN = 1;//该条成长记录是别人发布的动态，但是当前登录用户收录了
	
	private String newsGrowthLogOwnerUserName; //该条动态属于谁的成长日志(原创或者收录的动态),这里是系统中的用户名
	private long newsId; //自己发布的动态或者收录别人发布的动态的ID
	private String newsPublisherName; //该条动态发布人的姓名
	private String newsContent;//发布的动态内容;
	private int newsType;//该条动态信息的类型，带图像、带声音或者文字
	private int newsGrowthLogType;//该条成长记录（动态）是原创，还是收录
	
	private String newsTakeDownUserName;//如果信息是收录的，收录这条信息的收录人姓名
	private String userClass;//当前登录用户的小孩所在班级级别(只返回下列之一：小班、中班、大班)
	private int userJoinInClassDays;// 当前成长记录发布(动态)发布时，小朋友加入这个班级的天数
	/**
	 * 动态发布时间，精确到分钟，在服务器端进行时间判断，以服务器当前时间为准，客户端容易修改时间，存储格式如下:
	 * case 1:如果动态是当天发布的，取发布时间格式为：今天(这里注意空两格)14:28
	 * case 2:如果动态不是当天发布但是当年发布的，取发布格式为：7月23日(这里注意空两格)14:28
	 * case 3:如果动态不是当年发布的，取发布时间格式为：2012年5月6日(这里注意空两格)16:55
	 */
	private String newsPublishDate; 
	private String newPublisherFrom; //动态发布人来自的幼儿园
	private byte[] newsWithImage; //动态中的图片，不能与录音共存，注意动态的类型
	private byte[] newsWithVoice; //动态中的录音，不能与图片共存，注意动态的类型
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
