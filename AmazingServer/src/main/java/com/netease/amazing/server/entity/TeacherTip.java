package com.netease.amazing.server.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 老师和贴士的中间表
 * @author zhangxiaojie
 *
 */
@Entity
@Table(name = "tip_teacher")
public class TeacherTip extends IdEntity{
	private Teacher teacher;
	private Tip tip;
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne
	@JoinColumn(name="tip_id")
	public Tip getTip() {
		return tip;
	}
	public void setTip(Tip tip) {
		this.tip = tip;
	}
	
}
