package com.netease.amazing.sdk.dto;

import java.util.Date;
import java.util.List;


public class NoticeDTO {
	private long id;
	private String tittle;
	private Date noticeDate;
	private String content;
	private boolean needFeedBack;
	private List<Long> recieveObjsIDs;//所有应受到这个消息的id
	private String feedBack;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
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
	public List<Long> getRecieveObjsIDs() {
		return recieveObjsIDs;
	}
	public void setRecieveObjsIDs(List<Long> recieveObjsIDs) {
		this.recieveObjsIDs = recieveObjsIDs;
	}
	public String getFeedBack() {
		return feedBack;
	}
	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	public boolean isNeedFeedBack() {
		return needFeedBack;
	}
	public void setNeedFeedBack(boolean needFeedBack) {
		this.needFeedBack = needFeedBack;
	}
	
}
