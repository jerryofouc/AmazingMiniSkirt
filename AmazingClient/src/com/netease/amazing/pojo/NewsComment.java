package com.netease.amazing.pojo;
/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *   针对动态的评论
 */
public class NewsComment {
	public static int NEWS_COMMENT_COUNT_FOR_INDEX = 15;
	public static int NEWS_COMMENT_LIKE_TYPE = 1; //该评论仅表示喜欢这个动态
	public static int NEWS_COMMENT_TAKE_DOWN_TYPE = 2;//该评论仅表示收录了这个动态
	public static int NEWS_COMMENT_COMMON_TYPE = 0; //该评论表示这个动态为普通动态
	
	private long newsId; //该条评论属于id为newsId的动态
	private long newsCommentId; //该条评论的ID
	private long newsCommentPublisherId;// 该条评论发布人的ID，便于他人回复该条评论时，关联到数据库
	private String newsCommmentPublisherName; //该条评论的发布人姓名

	/**
	 * 该条评论回复的对象，可以为空，
	 *    case 1:如果为空，则前段展示为效果如下     李晓明的妈妈(newsCommentPublisher)：这个动态不错(newsComment)
	 *    case 2:如果不为空，则前段展示效果如下      王明的妈妈(newsCommentPublisher) 回复 李晓明 的妈妈（newsCommentTo）：我也觉得不错（newsComment）
	 */
	private String newsCommentTo;
	
	private String newsComment; //该条评论的内容
	private int newsCommentType;//该条评论的类型，如上常量所示
	
	
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
