package com.netease.amazing.sdk.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountRestClientTest {
	private static final String BASE_URL = "http://localhost:8080/MiniSkirtServer";
	private static final String USER_NAME = "zhanxiaojie";
	private static final String PASSWORD = "123456";
	@Test
	public void loginTest(){
		//AccountRestClient accountRestClient = new AccountRestClient(BASE_URL,USER_NAME,PASSWORD);
		assertTrue(AccountRestClient.testLogin(BASE_URL,USER_NAME, PASSWORD));
	}
}
