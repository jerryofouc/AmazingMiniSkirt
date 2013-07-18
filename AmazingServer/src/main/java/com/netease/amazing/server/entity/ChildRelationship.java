package com.netease.amazing.server.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "child_child")
public class ChildRelationship extends IdEntity{
	
	private Child childFrom;
	private Child childTo;
	
	@ManyToOne
	@JoinColumn(name="child_from_id")
	public Child getChildFrom() {
		return childFrom;
	}
	public void setChildFrom(Child childFrom) {
		this.childFrom = childFrom;
	}
	
	@ManyToOne
	@JoinColumn(name="child_to_id")
	public Child getChildTo() {
		return childTo;
	}
	public void setChildTo(Child childTo) {
		this.childTo = childTo;
	}
	
}
