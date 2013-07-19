package com.netease.amazing.sdk.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;


public abstract class AbstractBaseClient {
	HttpClient httpclient = new DefaultHttpClient();
	public String baseUrl;
	protected String loginName;
	protected String password;
	public AbstractBaseClient(String baseUrl, String loginName, String password) {
		this.baseUrl = baseUrl;
		this.loginName = loginName;
		this.password = password;
	}
}
