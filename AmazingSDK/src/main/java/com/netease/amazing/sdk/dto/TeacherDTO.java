package com.netease.amazing.sdk.dto;

public class TeacherDTO {
	private String name;
	private String fixLine;//固话
	private String mobilePhone;//移动电话
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
	
}
