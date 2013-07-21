package com.netease.amazing.server.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 用户订阅表
 * @author Administrator
 *
 */
@Entity
@Table(name = "user_tweet")
public class UserTweet extends IdEntity{
	private User user;
	private Tweet tweet;
	public enum PublicRelation{
		FROM_TEACHER,
		FROM_PARENT
	}
	private PublicRelation pubRelation;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="tweet_id")
	public Tweet getTweet() {
		return tweet;
	}
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
	
	@Enumerated(EnumType.STRING)
	public PublicRelation getPubRelation() {
		return pubRelation;
	}
	public void setPubRelation(PublicRelation pubRelation) {
		this.pubRelation = pubRelation;
	}
	
	
	
}
