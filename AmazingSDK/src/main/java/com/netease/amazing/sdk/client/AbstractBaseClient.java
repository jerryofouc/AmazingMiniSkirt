package com.netease.amazing.sdk.client;

import com.sun.jersey.api.client.Client;

public abstract class AbstractBaseClient {
	protected static Client client = Client.create();
	public String baseUrl;
	protected String loginName;
	protected String password;
	public AbstractBaseClient(String baseUrl, String loginName, String password) {
		this.baseUrl = baseUrl;
		this.loginName = loginName;
		this.password = password;
	}
}
