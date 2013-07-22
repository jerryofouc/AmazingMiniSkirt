package com.netease.amazing.pojo;
import java.io.Serializable;
import java.util.Date;

import com.netease.amazing.util.StringCuter;


public class Notice implements Serializable{
	private long id;
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
		if(content.length()<length) return content;
		else {
			return StringCuter.cutStr(content,72);
		}
	}
	
	public String cutTitle(int length,String encoder) {
		if(title.length()<length) {
			return title;
		}
		else {
			return StringCuter.cutStr(title, 17);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
