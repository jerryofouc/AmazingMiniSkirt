package com.netease.amazing.sdk.dto;


public class NewsDTO {
	public static final int RELATIONSHIP_TEACHER = 1;
	public static final int RELATIONSHIP_CLASSMATE = 2;
	public static final int RELATIONSHIP_FRIEND = 2;
	public enum TweetType{
		TEXT,
		WITH_VOICE,
		WITH_PICTURE
	}
	private long newsId;
	private long newsPublisherId; //动态发布人姓名， 当前登录用户的通讯录中所有联系人的动态都可以展示出来
	private int newsPublisherRelationship; //动态发布人与当前登录用户的关系
	private String newsContent;//发布的动态内容;
	private TweetType newsType;//该条动态信息的类型，参考上面常量
	/**
	 * 动态发布时间，精确到分钟，在服务器端进行时间判断，以服务器当前时间为准，客户端容易修改时间，存储格式如下:
	 * case 1:如果动态是当天发布的，取发布时间格式为：今天(这里注意空两格)14:28
	 * case 2:如果动态不是当天发布但是当年发布的，取发布格式为：7月23日(这里注意空两格)14:28
	 * case 3:如果动态不是当年发布的，取发布时间格式为：2012年5月6日(这里注意空两格)16:55
	 */
	private String newsPublishDate; 
	private String newPublisherFrom; //动态发布人来自的幼儿园
	private boolean newsCurrentUserLike;//该动态是否被当前登录用户喜欢
	private boolean newsCurrentUserTakeDown;//该动态是否被当前登录用户收录
	public long getNewsId() {
		return newsId;
	}
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	public int getNewsPublisherRelationship() {
		return newsPublisherRelationship;
	}
	public void setNewsPublisherRelationship(int newsPublisherRelationship) {
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
	public long getNewsPublisherId() {
		return newsPublisherId;
	}
	public void setNewsPublisherId(long newsPublisherId) {
		this.newsPublisherId = newsPublisherId;
	}
	
}
