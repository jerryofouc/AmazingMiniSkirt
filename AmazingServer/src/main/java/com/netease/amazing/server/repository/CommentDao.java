package com.netease.amazing.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.Comment;


public interface CommentDao extends PagingAndSortingRepository<Comment, Long>{
}
