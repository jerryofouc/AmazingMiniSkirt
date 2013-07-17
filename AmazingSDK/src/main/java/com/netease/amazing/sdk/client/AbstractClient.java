package com.netease.amazing.sdk.client;

public abstract class AbstractClient {
	public String baseUrl;
	protected String loginName;
	protected String password;
	public AbstractClient(String baseUrl, String loginName, String password) {
		this.baseUrl = baseUrl;
		this.loginName = loginName;
		this.password = password;
	}
}
