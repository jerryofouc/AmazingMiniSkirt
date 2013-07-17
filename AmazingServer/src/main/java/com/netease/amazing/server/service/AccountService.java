package com.netease.amazing.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.netease.amazing.server.entity.User;
import com.netease.amazing.server.repository.UserDao;

/**
 * 用户服务类
 * @author zhangxiaojie
 *
 */
@Component
@Transactional(readOnly = true)
public class AccountService {
	public static final String HASH_ALGORITHM = "SHA-1";
	private static final int SALT_SIZE = 8;
	public static final int HASH_INTERATIONS = 1024;
	@Autowired
	private UserDao userDao;

	public User findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}
	
	public User findUserById(long id){
		return userDao.findOne(id);
	}
	
	public static void main(String args[]){
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1("123456".getBytes(), salt, HASH_INTERATIONS);
		System.out.println("salt:" + Encodes.encodeHex(salt));
		System.out.println("hashPassword:" + Encodes.encodeHex(hashPassword));
	}
}
