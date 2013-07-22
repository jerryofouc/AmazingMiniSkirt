package com.netease.amazing.server.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.netease.amazing.server.entity.User;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserDaoTest  extends SpringTransactionalTestCase{
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void findTasksByUserId() throws Exception {
		User user = userDao.findOne(1L);
		System.out.println(user.getName());
	}
}
