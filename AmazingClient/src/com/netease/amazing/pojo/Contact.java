package com.netease.amazing.pojo;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Contact implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static int RELATIONSHIP_TEACHER = 1;
	public final static int RELATIONSHIP_CLASSMATE = 2;
	public final static int RELATIONSHIP_FRIEND = 3;
	
	private String name;  //联系人姓名
	private String nickName;  //昵称
	private String birthday;  //生日
	private String gender;  //性别
	
	private String nameOfDad; //爸爸的姓名
	private String phoneOfDad;  //爸爸的电话
	
	private String nameOfMum;  //妈妈的姓名
	private String phoneOfMum;  //妈妈的电话
	
	private String fromSchool;  //来自的学校
	private String fromClass;  //来自的班级
	
	private Bitmap img;  //联系人头像
	
	//  与当前用户的关系， 参考final static中的定义 
	private int relationship;  
	
	private String phoneOfTeacher; //当relationship为RELATIONSHIP_TEACHER时，设置教师的固定电话
	private String mobileOfTeacher; //当relationship为RELATIONSHIP_TEACHER时，设置教师的手机号
	
	public Contact(String name, String nickName, String birthday,
			String gender, String nameOfDad, String phoneOfDad,
			String nameOfMum, String phoneOfMum, String fromSchool,
			String fromClass, Bitmap img, int relationship,
			String phoneOfTeacher, String mobileOfTeacher) {
		super();
		this.name = name;
		this.nickName = nickName;
		this.birthday = birthday;
		this.gender = gender;
		this.nameOfDad = nameOfDad;
		this.phoneOfDad = phoneOfDad;
		this.nameOfMum = nameOfMum;
		this.phoneOfMum = phoneOfMum;
		this.fromSchool = fromSchool;
		this.fromClass = fromClass;
		this.img = img;
		this.relationship = relationship;
		this.phoneOfTeacher = phoneOfTeacher;
		this.mobileOfTeacher = mobileOfTeacher;
	}

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

	public String getNameOfDad() {
		return nameOfDad;
	}

	public void setNameOfDad(String nameOfDad) {
		this.nameOfDad = nameOfDad;
	}

	public String getPhoneOfDad() {
		return phoneOfDad;
	}

	public void setPhoneOfDad(String phoneOfDad) {
		this.phoneOfDad = phoneOfDad;
	}

	public String getNameOfMum() {
		return nameOfMum;
	}

	public void setNameOfMum(String nameOfMum) {
		this.nameOfMum = nameOfMum;
	}

	public String getPhoneOfMum() {
		return phoneOfMum;
	}

	public void setPhoneOfMum(String phoneOfMum) {
		this.phoneOfMum = phoneOfMum;
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

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

	public String getPhoneOfTeacher() {
		return phoneOfTeacher;
	}

	public void setPhoneOfTeacher(String phoneOfTeacher) {
		this.phoneOfTeacher = phoneOfTeacher;
	}

	public String getMobileOfTeacher() {
		return mobileOfTeacher;
	}

	public void setMobileOfTeacher(String mobileOfTeacher) {
		this.mobileOfTeacher = mobileOfTeacher;
	}
}
