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

import com.netease.amazing.sdk.dto.NewsDTO.TweetType;

@Entity
@Table(name = "tweet")
public class Tweet extends IdEntity{
	public String contents;
	public Date createTime;
	public TweetType type; 
	public User user;//发送tweet的人
	public List<Comment> comments;
	public List<UserTweet> recievers;//接受tweet的所有人
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
	
	@Enumerated(EnumType.STRING)
	public TweetType getType() {
		return type;
	}
	public void setType(TweetType type) {
		this.type = type;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="tweet")
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="tweet")
	public List<UserTweet> getRecievers() {
		return recievers;
	}
	public void setRecievers(List<UserTweet> recievers) {
		this.recievers = recievers;
	}
	
	
	
}
