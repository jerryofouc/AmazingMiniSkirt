package com.netease.amazing.server.web;

import org.apache.shiro.SecurityUtils;

import com.netease.amazing.server.service.account.ShiroDbRealm.ShiroUser;

public abstract class BaseController {
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	protected Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
