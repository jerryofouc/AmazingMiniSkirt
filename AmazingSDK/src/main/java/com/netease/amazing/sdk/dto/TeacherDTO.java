package com.netease.amazing.sdk.dto;

public class TeacherDTO {
	private long id;
	private String name;
	private String fixLine;//�̻�
	private String mobilePhone;//�ƶ��绰
	private String headPicPath;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFixLine() {
		return fixLine;
	}
	public void setFixLine(String fixLine) {
		this.fixLine = fixLine;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHeadPicPath() {
		return headPicPath;
	}
	public void setHeadPicPath(String headPicPath) {
		this.headPicPath = headPicPath;
	}
	
	
}
