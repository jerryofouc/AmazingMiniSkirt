package com.netease.amazing.sdk.dto;

public class UserDTO {
	private long id;
	private String loginName;
	private String name;
	private String frontCover;
	private String headPic;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFrontCover() {
		return frontCover;
	}
	public void setFrontCover(String frontCover) {
		this.frontCover = frontCover;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
}
