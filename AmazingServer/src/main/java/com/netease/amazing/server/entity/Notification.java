package com.netease.amazing.server.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="notification")
public class Notification extends IdEntity
{
  private String tittle;
  private String contents;
  private Date createTime;
  private boolean isNeedFeedBack;
  private String feedBack;
  private Teacher teacher;
  private List<ParentNotification> parents;

  public String getTittle()
  {
    return this.tittle;
  }
  public void setTittle(String tittle) {
    this.tittle = tittle;
  }
  public String getContents() {
    return this.contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  public Date getCreateTime() {
    return this.createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public boolean isNeedFeedBack() {
    return this.isNeedFeedBack;
  }
  public void setNeedFeedBack(boolean isNeedFeedBack) {
    this.isNeedFeedBack = isNeedFeedBack;
  }
  @ManyToOne
  @JoinColumn(name="teacher_id")
  public Teacher getTeacher() {
    return this.teacher;
  }
  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  @OneToMany(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.ALL}, mappedBy="notification")
  public List<ParentNotification> getParents() {
    return this.parents;
  }
  public void setParents(List<ParentNotification> parents) {
    this.parents = parents;
  }
  public String getFeedBack() {
    return this.feedBack;
  }
  public void setFeedBack(String feedBack) {
    this.feedBack = feedBack;
  }
}