package com.netease.amazing.server.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tweet_comment")
public class Comment extends IdEntity{
	private String content;
	public enum CommentType{
		LIKE,
		INCLUDE,//收录
		NORMAL//普通
	}
	private CommentType type;
	private Tweet tweet;
	private User user;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CommentType getType() {
		return type;
	}
	public void setType(CommentType type) {
		this.type = type;
	}
	@ManyToOne
	@JoinColumn(name="tweet_id")
	public Tweet getTweet() {
		return tweet;
	}
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
