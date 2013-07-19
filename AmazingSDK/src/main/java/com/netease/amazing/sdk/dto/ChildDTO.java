package com.netease.amazing.sdk.dto;

public class ChildDTO {
	private String name;
	private String fatherName;
	private String birthday;
	private String nickName;  //昵称
	private String fatherTelephone;
	private String motherName;
	private String motherTelephone;
	private String fromSchool;//来自学校
	private String fromClass;//来自班级
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getFatherTelephone() {
		return fatherTelephone;
	}
	public void setFatherTelephone(String fatherTelephone) {
		this.fatherTelephone = fatherTelephone;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getMotherTelephone() {
		return motherTelephone;
	}
	public void setMotherTelephone(String motherTelephone) {
		this.motherTelephone = motherTelephone;
	}
	public String getFromSchool() {
		return fromSchool;
	}
	public void setFromSchool(String fromSchool) {
		this.fromSchool = fromSchool;
	}
	public String getFromClass() {
		return fromClass;
	}
	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
