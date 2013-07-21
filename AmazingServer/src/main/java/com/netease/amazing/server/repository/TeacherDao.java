package com.netease.amazing.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.netease.amazing.server.entity.Teacher;


public interface TeacherDao  extends PagingAndSortingRepository<Teacher, Long>{

}
