package com.netease.amazing.sdk.dto;

import java.util.List;


public class ContactDTO {
	private String name;  //��ϵ������
	private String nickName;  //�ǳ�
	private String birthday;  //����
	private String gender;  //�Ա�
	private String fromSchool;//����ѧУ
	private String fromClass;//���԰༶
	List<TeacherDTO> teachers;//��ʦ
	List<ChildDTO> friends;//����
	List<ChildDTO> classMates;//�༶ͬѧ
	
	
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
