package com.netease.amazing.sdk.dto;

public class TeacherDTO {
	private String id;
	private String name;
	private String fixLine;//�̻�
	private String mobilePhone;//�ƶ��绰
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
