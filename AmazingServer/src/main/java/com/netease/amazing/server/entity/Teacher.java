package com.netease.amazing.server.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "teacher")
public class Teacher extends IdEntity{
	private String fixLine;
	private String name;
	private String telephone;
	private String description;
	private String headPicPath;
	private Class klass;
	private User user;
	private List<Notification> notifications; 
	public String getFixLine() {
		return fixLine;
	}
	public void setFixLine(String fixLine) {
		this.fixLine = fixLine;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name="class_id")
	public Class getKlass() {
		return klass;
	}
	public void setKlass(Class klass) {
		this.klass = klass;
	}
	@OneToOne(mappedBy="teacher")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="teacher")
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	public String getHeadPicPath() {
		return headPicPath;
	}
	public void setHeadPicPath(String headPicPath) {
		this.headPicPath = headPicPath;
	}
}
