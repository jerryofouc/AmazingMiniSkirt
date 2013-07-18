package com.netease.amazing.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.Parent;


public interface ParentDao extends PagingAndSortingRepository<Parent, Long>{

}
