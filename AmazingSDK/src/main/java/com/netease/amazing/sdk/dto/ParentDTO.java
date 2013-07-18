package com.netease.amazing.sdk.dto;

public class ParentDTO {
	private String name;
	private String telphone;
	private Gender gender;//ÐÔ±ð£¬°Ö/Âè
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
