package com.netease.amazing.server.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "kindergarden")
public class Kindergarden extends IdEntity{
	private String name;
	private String address;
	
	private List<Class> classes;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="kindergarden")
	public List<Class> getClasses() {
		return classes;
	}
	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}
	
	
}
