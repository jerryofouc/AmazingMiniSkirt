package com.netease.amazing.pojo;
import java.io.Serializable;
import java.util.Date;


public class Notice implements Serializable{
	private int id;
	private String title;
	private boolean isUpload;
	private Date noticeDate;
	private String content;
	private boolean isNeedFeedBack;
	private Object[] attachments;
	
	
	
	public Notice() {
		
	}
	public Notice(String title, boolean isUpload, Date noticeDate,
			String content, boolean isNeedFeedBack, Object[] attachments) {
		super();
		this.title = title;
		this.isUpload = isUpload;
		this.noticeDate = noticeDate;
		this.content = content;
		this.isNeedFeedBack = isNeedFeedBack;
		this.attachments = attachments;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isUpload() {
		return isUpload;
	}
	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Object[] getAttachments() {
		return attachments;
	}
	public void setAttachments(Object[] attachments) {
		this.attachments = attachments;
	}


	public boolean isNeedFeedBack() {
		return isNeedFeedBack;
	}


	public void setNeedFeedBack(boolean isNeedFeedBack) {
		this.isNeedFeedBack = isNeedFeedBack;
	}
	
	public String cutContent(int length,String encoder) {
		return content.substring(0, 30) +"...";
	}
	
	public String cutTitle(int length,String encoder) {
		return title.substring(0, 10) +"...";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Notice(Boolean b) {
		setId(1000);
		setTitle("2013年暑假致家长的关于假期注意孩子安全的通知");
		setNoticeDate(new Date(System.currentTimeMillis()));
		setContent(" 五一节假期在即，为进一步加强学生安全教育，确保节日期间学生的人身和财产安全，维护学校稳定与和谐，现将有关要求通知如下:" +
							"一、各校要在放假之前，采取班会、集队等形式，对所有学生进行一次全面的安全教育，重点进行防火、防盗、防溺水、防食物中毒和交通安全等方面的教育，进一步提高"+
							"学生防范意外伤害意识和自我保护能力。假期期间，人、车流量大，要教育学生注意外出安全，严格遵守交通规则，不得驾驶机动车辆，拒绝乘坐无牌、无证车辆和超载车"+
							"辆，谨防交通事故的发生。二、不主张、不提倡学生独自在“五一”节假期外出游玩。严禁学校、团支部、学生会、班级等名义组织学生参加各种类型的春游活动。"+
							"三、切实做好放假前的安全隐患排查，做好记录，不得流于形式。"+
							"四、加强家校联系，通过印发《告家长书》、家访等形式，告知家长或监护人承担起孩子的安全监护责任，在节日、周末和放学后加强对孩子的监管。"+
							"五、做好“五一“假期的值日安排，落实值班领导和值班教师责任，做好值班记录，确保重大紧急情况和重要信息第一时间准确报送。同时，把假期值班表报送教育科邮箱：");
		
		setNeedFeedBack(false);
		setUpload(false);
		setAttachments(null);
	}
}
