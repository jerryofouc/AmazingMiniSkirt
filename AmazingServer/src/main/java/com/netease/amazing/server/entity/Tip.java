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

/**
 * 家长对孩子的提醒
 * @author zhangxiaojie
 *
 */

@Entity
@Table(name = "tip")
public class Tip extends IdEntity{
	private String tittle;
	private String contents;
	private Date createTime;
	private User parent;//发tip的必须是家长
	private List<TeacherTip> teachers;
	
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
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="tip")
	public List<TeacherTip> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<TeacherTip> teachers) {
		this.teachers = teachers;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
}
