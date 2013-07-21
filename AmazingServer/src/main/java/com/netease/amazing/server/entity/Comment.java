package com.netease.amazing.server.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.netease.amazing.sdk.dto.NewsCommentsDTO.CommentType;


@Entity
@Table(name = "tweet_comment")
public class Comment extends IdEntity{
	private String content;
	private CommentType type;
	private Tweet tweet;
	private User user;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Enumerated(EnumType.STRING)
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
