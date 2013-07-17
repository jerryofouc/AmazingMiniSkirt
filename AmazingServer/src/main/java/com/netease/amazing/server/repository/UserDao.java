package com.netease.amazing.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>{
	public User findUserByLoginName(String loginName);
}
