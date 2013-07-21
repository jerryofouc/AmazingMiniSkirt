package com.netease.amazing.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.UserTweet;


public interface UserTweetDao extends PagingAndSortingRepository<UserTweet, Long>{

}
