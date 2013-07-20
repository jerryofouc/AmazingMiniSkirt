package com.netease.amazing.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification extends IdEntity{
	private String tittle;
	private String contents;
	private Date createTime;
	private boolean isNeedFeedBack;
	private Teacher teacher;
	private List<ParentNotification> parents;
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public boolean isNeedFeedBack() {
		return isNeedFeedBack;
	}
	public void setNeedFeedBack(boolean isNeedFeedBack) {
		this.isNeedFeedBack = isNeedFeedBack;
	}
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="notification")
	public List<ParentNotification> getParents() {
		return parents;
	}
	public void setParents(List<ParentNotification> parents) {
		this.parents = parents;
	}
	
}
