package com.netease.amazing.server.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class Class extends IdEntity{
	private String name;
	private String description;
	private List<Child> children;
	private Kindergarden kindergarden;
	private List<Teacher> teachers;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="klass")
	public List<Child> getChildren() {
		return children;
	}
	public void setChildren(List<Child> children) {
		this.children = children;
	}
	
	@ManyToOne
	@JoinColumn(name="kindergarden_id")
	public Kindergarden getKindergarden() {
		return kindergarden;
	}
	public void setKindergarden(Kindergarden kindergarden) {
		this.kindergarden = kindergarden;
	}
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="klass")
	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	
}
