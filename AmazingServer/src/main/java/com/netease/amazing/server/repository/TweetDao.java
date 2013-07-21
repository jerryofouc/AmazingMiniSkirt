package com.netease.amazing.server.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.Comment;
import com.netease.amazing.server.entity.Tweet;


public interface TweetDao extends PagingAndSortingRepository<Tweet, Long>{
	@Query("select t from com.netease.amazing.server.entity.Tweet as t inner join t.recievers as r where r.user.id=?1")
	public List<Tweet> findLatestTweets(long userId,Pageable pageable);
	@Query("select c from com.netease.amazing.server.entity.Comment as c inner join c.user as u where u.id=?1 and c.type =?2 and c.tweet.id=?3")
	public List<Comment> findComment(long userId, Comment.CommentType type, long tweetId);
}
