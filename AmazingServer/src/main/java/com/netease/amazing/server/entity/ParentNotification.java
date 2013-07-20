package com.netease.amazing.server.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "parent_notification")
public class ParentNotification extends IdEntity{
	
	private User parent;
	private Notification notification;
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}
	
	@ManyToOne
	@JoinColumn(name="notification_id")
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
}
