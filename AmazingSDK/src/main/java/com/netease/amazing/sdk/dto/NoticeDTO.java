package com.netease.amazing.sdk.dto;

import java.util.Date;


public class NoticeDTO {
	private long id;
	private String title;
	private boolean isUpload;
	private Date noticeDate;
	private String content;
	private boolean isNeedFeedBack;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public boolean isNeedFeedBack() {
		return isNeedFeedBack;
	}
	public void setNeedFeedBack(boolean isNeedFeedBack) {
		this.isNeedFeedBack = isNeedFeedBack;
	}
	
}
