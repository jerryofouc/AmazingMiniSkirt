package com.netease.amazing.sdk.client;

import org.junit.Test;

public class AccountRestClientTest {
	private static final String BASE_URL = "http://localhost:8080/MiniSkirtServer";
	private static final String USER_NAME = "zhangxiaojie";
	private static final String PASSWORD = "123456";
	@Test
	public void loginTest(){
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD);
	}
}
