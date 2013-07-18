package com.netease.amazing.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "child")
public class Child extends IdEntity{
	private String name;
	private Gender gender;
	private String description;
	private Date birthday;
	private Class klass;
	private List<Parent> parents;
	private List<ChildRelationship> friends;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="child")
	public List<Parent> getParents() {
		return parents;
	}
	
	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}
	
	@ManyToOne
	@JoinColumn(name="class_id")
	public Class getKlass() {
		return klass;
	}
	public void setKlass(Class klass) {
		this.klass = klass;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="childFrom")
	public List<ChildRelationship> getFriends() {
		return friends;
	}
	public void setFriends(List<ChildRelationship> friends) {
		this.friends = friends;
	}
}