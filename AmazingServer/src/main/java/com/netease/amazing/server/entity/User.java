package com.netease.amazing.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;




@Entity
@Table(name = "user")
public class User extends IdEntity {
	private String loginName;
	private String name;
	private String password;
	private String salt;
	private Date registerDate;
	private Child child;
	private List<Tweet> tweets;
	private boolean hasLogin;
	public enum Role{
		PARENT,
		TEACHER
	}
	private Role role;//角色：分为老师和家长两个角色
	private Teacher teacher;
	private List<ParentNotification> notificatoins; 
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@OneToOne
    @JoinColumn(name="teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="parent")
	public List<ParentNotification> getNotificatoins() {
		return notificatoins;
	}
	public void setNotificatoins(List<ParentNotification> notificatoins) {
		this.notificatoins = notificatoins;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user")
	public List<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	@OneToOne
    @JoinColumn(name="child_id")
	public Child getChild() {
		return child;
	}
	public void setChild(Child child) {
		this.child = child;
	}
	
	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		List<String> roleList = new ArrayList<String>();
		roleList.add(role.toString());
		return roleList;
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
	}
	public boolean isHasLogin() {
		return hasLogin;
	}
	public void setHasLogin(boolean hasLogin) {
		this.hasLogin = hasLogin;
	}
	
}
