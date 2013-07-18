package com.netease.amazing.sdk.dto;

import java.util.List;


public class ContactDTO {
	private String name;  //联系人姓名
	private String nickName;  //昵称
	private String birthday;  //生日
	private String gender;  //性别
	private String fromSchool;//来自学校
	private String fromClass;//来自班级
	List<TeacherDTO> teachers;//老师
	List<ChildDTO> friends;//朋友
	List<ChildDTO> classMates;//班级同学
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public List<TeacherDTO> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<TeacherDTO> teachers) {
		this.teachers = teachers;
	}
	public List<ChildDTO> getFriends() {
		return friends;
	}
	public void setFriends(List<ChildDTO> friends) {
		this.friends = friends;
	}
	public List<ChildDTO> getClassMates() {
		return classMates;
	}
	public void setClassMates(List<ChildDTO> classMates) {
		this.classMates = classMates;
	}
	
}
