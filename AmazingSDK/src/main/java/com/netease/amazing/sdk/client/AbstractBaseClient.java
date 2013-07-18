package com.netease.amazing.sdk.client;

public abstract class AbstractBaseClient {
	public String baseUrl;
	protected String loginName;
	protected String password;
	public AbstractBaseClient(String baseUrl, String loginName, String password) {
		this.baseUrl = baseUrl;
		this.loginName = loginName;
		this.password = password;
	}
}
